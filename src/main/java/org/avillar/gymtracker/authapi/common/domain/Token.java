package org.avillar.gymtracker.authapi.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Token {

  private String type;

  private String value;
}
