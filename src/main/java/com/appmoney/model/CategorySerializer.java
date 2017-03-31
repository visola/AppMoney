package com.appmoney.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CategorySerializer extends StdSerializer<Category> {

  private static final long serialVersionUID = 1L;

  public CategorySerializer() {
    this(null);
  }

  protected CategorySerializer(Class<Category> t) {
    super(t);
  }

  @Override
  public void serialize(Category category, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    gen.writeNumberField("id", category.getId());
    gen.writeStringField("name", category.getName());
    gen.writeObjectField("owner", category.getOwner());

    if (category.getParent() != null) {
      gen.writeObjectFieldStart("parent");
      gen.writeNumberField("id", category.getParent().getId());
      gen.writeStringField("name", category.getParent().getName());
      gen.writeEndObject();
    }

    gen.writeEndObject();
  }

}
