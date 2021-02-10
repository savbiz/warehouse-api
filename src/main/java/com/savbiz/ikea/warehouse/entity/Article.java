package com.savbiz.ikea.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "article_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "available_stock", nullable = false)
  private Integer availableStock;

}
