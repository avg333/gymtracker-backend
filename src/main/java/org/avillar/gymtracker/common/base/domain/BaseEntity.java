package org.avillar.gymtracker.common.base.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@BatchSize(size = 20)
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @CreatedBy private String createdBy;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedBy private String lastModifiedBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedAt;

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (null == o || getClass() != o.getClass()) return false;
    final BaseEntity baseEntity = (BaseEntity) o;
    return id != null && baseEntity.id != null && id.equals(baseEntity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "BaseEntity{"
        + "id="
        + id
        + ", createdBy='"
        + createdBy
        + '\''
        + ", createdAt="
        + createdAt
        + ", lastModifiedBy='"
        + lastModifiedBy
        + '\''
        + ", lastModifiedAt="
        + lastModifiedAt
        + '}';
  }
}
