package edu.skku.dealistic.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Website reference
 *
 * @author Junhyun Kim
 */

@Entity
@Data
public class Reference {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

}
