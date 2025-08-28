package com.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "rulename", schema = "demo")
public class RuleName implements BaseEntity<RuleName>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 125)
    @NotBlank(message = "Rule name is mandatory")
    @Column(name = "name", length = 125)
    private String name;

    @Size(max = 125)
    @Column(name = "description", length = 125)
    private String description;

    @Size(max = 125)
    @Column(name = "json", length = 125)
    private String json;

    @Size(max = 512)
    @Column(name = "template", length = 512)
    private String template;

    @Size(max = 125)
    @Column(name = "sqlStr", length = 125)
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sqlPart", length = 125)
    private String sqlPart;

    public RuleName(String ruleName, String description, String json, String template, String sql, String sqlPart) {
        this.name = ruleName;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sql;
        this.sqlPart = sqlPart;
    }

    @Override
    public void update(RuleName ruleName) {
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sqlStr = ruleName.getSqlStr();
        this.sqlPart = ruleName.getSqlPart();
    }
}