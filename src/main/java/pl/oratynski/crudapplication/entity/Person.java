package pl.oratynski.crudapplication.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}