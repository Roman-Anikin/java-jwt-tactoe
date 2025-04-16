package domain.model;

import io.hypersistence.utils.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Arrays;

@Entity
@Table(name = "game_field")
public class GameField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Type(value = IntArrayType.class)
    @Column(name = "field", columnDefinition = "integer[][]")
    private int[][] field;

    public GameField() {
        field = new int[3][3];
        for (int[] row : field) {
            Arrays.fill(row, -1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public void setX(int row, int col) {
        field[row][col] = 1;
    }

    public void setO(int row, int col) {
        field[row][col] = 0;
    }

    @Override
    public String toString() {
        return "GameField{" +
                "id=" + id +
                ", field=" + Arrays.toString(field) +
                '}';
    }
}
