package com.example.app;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.api.ResponseFieldMarshaller;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.ResponseWriter;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("Apollo GraphQL")
public final class ProjectMutation implements Mutation<ProjectMutation.Data, ProjectMutation.Data, ProjectMutation.Variables> {
  public static final String OPERATION_ID = "2e2094c40183e5540ba8bedc32656545db63698e67b7f603af32b1abbba7f94f";

  public static final String QUERY_DOCUMENT = "mutation Project($id: String!, $name: String!, $description: String!, $status: String!) {\n"
      + "  createProject(id: $id, name: $name, description: $description, status: $status) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    name\n"
      + "    description\n"
      + "    status\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "Project";
    }
  };

  private final ProjectMutation.Variables variables;

  public ProjectMutation(@NotNull String id, @NotNull String name, @NotNull String description,
      @NotNull String status) {
    Utils.checkNotNull(id, "id == null");
    Utils.checkNotNull(name, "name == null");
    Utils.checkNotNull(description, "description == null");
    Utils.checkNotNull(status, "status == null");
    variables = new ProjectMutation.Variables(id, name, description, status);
  }

  @Override
  public String operationId() {
    return OPERATION_ID;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public ProjectMutation.Data wrapData(ProjectMutation.Data data) {
    return data;
  }

  @Override
  public ProjectMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public OperationName name() {
    return OPERATION_NAME;
  }

  public static final class Builder {
    private @NotNull String id;

    private @NotNull String name;

    private @NotNull String description;

    private @NotNull String status;

    Builder() {
    }

    public Builder id(@NotNull String id) {
      this.id = id;
      return this;
    }

    public Builder name(@NotNull String name) {
      this.name = name;
      return this;
    }

    public Builder description(@NotNull String description) {
      this.description = description;
      return this;
    }

    public Builder status(@NotNull String status) {
      this.status = status;
      return this;
    }

    public ProjectMutation build() {
      Utils.checkNotNull(id, "id == null");
      Utils.checkNotNull(name, "name == null");
      Utils.checkNotNull(description, "description == null");
      Utils.checkNotNull(status, "status == null");
      return new ProjectMutation(id, name, description, status);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final @NotNull String name;

    private final @NotNull String description;

    private final @NotNull String status;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id, @NotNull String name, @NotNull String description,
        @NotNull String status) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.status = status;
      this.valueMap.put("id", id);
      this.valueMap.put("name", name);
      this.valueMap.put("description", description);
      this.valueMap.put("status", status);
    }

    public @NotNull String id() {
      return id;
    }

    public @NotNull String name() {
      return name;
    }

    public @NotNull String description() {
      return description;
    }

    public @NotNull String status() {
      return status;
    }

    @Override
    public Map<String, Object> valueMap() {
      return Collections.unmodifiableMap(valueMap);
    }

    @Override
    public InputFieldMarshaller marshaller() {
      return new InputFieldMarshaller() {
        @Override
        public void marshal(InputFieldWriter writer) throws IOException {
          writer.writeString("id", id);
          writer.writeString("name", name);
          writer.writeString("description", description);
          writer.writeString("status", status);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("createProject", "createProject", new UnmodifiableMapBuilder<String, Object>(4)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .put("name", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "name")
        .build())
      .put("description", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "description")
        .build())
      .put("status", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "status")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateProject createProject;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateProject createProject) {
      this.createProject = createProject;
    }

    public @Nullable CreateProject createProject() {
      return this.createProject;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createProject != null ? createProject.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createProject=" + createProject
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.createProject == null) ? (that.createProject == null) : this.createProject.equals(that.createProject));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createProject == null) ? 0 : createProject.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateProject.Mapper createProjectFieldMapper = new CreateProject.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateProject createProject = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateProject>() {
          @Override
          public CreateProject read(ResponseReader reader) {
            return createProjectFieldMapper.map(reader);
          }
        });
        return new Data(createProject);
      }
    }
  }

  public static class CreateProject {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @Nullable String id;

    final @Nullable String name;

    final @Nullable String description;

    final @Nullable String status;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public CreateProject(@NotNull String __typename, @Nullable String id, @Nullable String name,
        @Nullable String description, @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.name = name;
      this.description = description;
      this.status = status;
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable String name() {
      return this.name;
    }

    public @Nullable String description() {
      return this.description;
    }

    public @Nullable String status() {
      return this.status;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], name);
          writer.writeString($responseFields[3], description);
          writer.writeString($responseFields[4], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateProject{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "name=" + name + ", "
          + "description=" + description + ", "
          + "status=" + status
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateProject) {
        CreateProject that = (CreateProject) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.description == null) ? (that.description == null) : this.description.equals(that.description))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= __typename.hashCode();
        h *= 1000003;
        h ^= (id == null) ? 0 : id.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (description == null) ? 0 : description.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<CreateProject> {
      @Override
      public CreateProject map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String name = reader.readString($responseFields[2]);
        final String description = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        return new CreateProject(__typename, id, name, description, status);
      }
    }
  }
}