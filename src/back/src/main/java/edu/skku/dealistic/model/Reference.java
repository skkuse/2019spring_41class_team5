package edu.skku.dealistic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Website reference
 *
 * @author Junhyun Kim
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

}
