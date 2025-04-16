package datasource.repository;

import domain.model.CurrentGame;
import domain.model.UserWinRate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<CurrentGame, UUID> {

    @EntityGraph(attributePaths = "gameField")
    List<CurrentGame> findByIsWaitingTrue();

    @EntityGraph(attributePaths = "gameField")
    @Query("SELECT cg FROM CurrentGame cg " +
            "WHERE (cg.xPlayer = :uuid OR cg.oPlayer = :uuid) " +
            "AND (cg.isDraw = true OR cg.winner IS NOT NULL)")
    List<CurrentGame> findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
            WITH user_games AS (
                SELECT\s
                    u.uuid AS user_uuid,
                    u.login,
                    COUNT(CASE WHEN cg.winner = u.uuid THEN 1 END)::float AS wins,
                    COUNT(CASE WHEN (cg.winner IS NOT NULL AND cg.winner != u.uuid)\s
                    OR cg.draw = true THEN 1 END)::float AS losses
                FROM users u
                LEFT JOIN current_game cg ON cg.x_player = u.uuid OR cg.o_player = u.uuid
                GROUP BY u.uuid, u.login
            )
            SELECT\s
                user_uuid AS uuid,
                login,
                CASE\s
                    WHEN losses = 0 THEN wins
                    ELSE wins / losses
                END AS ratio
            FROM user_games
            ORDER BY ratio DESC
            LIMIT :limit""", nativeQuery = true)
    List<UserWinRate> findUserWinRateStatsBy(@Param("limit") int limit);

}
