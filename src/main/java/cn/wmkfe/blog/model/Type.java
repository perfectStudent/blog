package cn.wmkfe.blog.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

public class Type {
    private Integer id;
    @NotEmpty(message = "分类名称不能为空")
    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}