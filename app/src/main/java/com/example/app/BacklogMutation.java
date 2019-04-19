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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Generated;

import type.CustomType;

@Generated("Apollo GraphQL")
public final class BacklogMutation implements Mutation<BacklogMutation.Data, BacklogMutation.Data, BacklogMutation.Variables> {
  public static final String OPERATION_ID = "499ab02b4de0d712f373410db9a887a2d26fea49efce3dd076728f564021c9e3";

  public static final String QUERY_DOCUMENT = "mutation Backlog($id: String!, $idProject: String!, $name: String!, $status: String!, $begindate: Date!, $enddate: Date!, $description: String!) {\n"
      + "  createBacklog(id: $id, idProject: $idProject, name: $name, status: $status, begindate: $begindate, enddate: $enddate, description: $description) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    idProject\n"
      + "    name\n"
      + "    status\n"
      + "    begindate\n"
      + "    enddate\n"
      + "    description\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "Backlog";
    }
  };

  private final BacklogMutation.Variables variables;

  public BacklogMutation(@NotNull String id, @NotNull String idProject, @NotNull String name,
      @NotNull String status, @NotNull Date begindate, @NotNull Date enddate,
      @NotNull String description) {
    Utils.checkNotNull(id, "id == null");
    Utils.checkNotNull(idProject, "idProject == null");
    Utils.checkNotNull(name, "name == null");
    Utils.checkNotNull(status, "status == null");
    Utils.checkNotNull(begindate, "begindate == null");
    Utils.checkNotNull(enddate, "enddate == null");
    Utils.checkNotNull(description, "description == null");
    variables = new BacklogMutation.Variables(id, idProject, name, status, begindate, enddate, description);
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
  public BacklogMutation.Data wrapData(BacklogMutation.Data data) {
    return data;
  }

  @Override
  public BacklogMutation.Variables variables() {
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

    private @NotNull String idProject;

    private @NotNull String name;

    private @NotNull String status;

    private @NotNull Date begindate;

    private @NotNull Date enddate;

    private @NotNull String description;

    Builder() {
    }

    public Builder id(@NotNull String id) {
      this.id = id;
      return this;
    }

    public Builder idProject(@NotNull String idProject) {
      this.idProject = idProject;
      return this;
    }

    public Builder name(@NotNull String name) {
      this.name = name;
      return this;
    }

    public Builder status(@NotNull String status) {
      this.status = status;
      return this;
    }

    public Builder begindate(@NotNull Date begindate) {
      this.begindate = begindate;
      return this;
    }

    public Builder enddate(@NotNull Date enddate) {
      this.enddate = enddate;
      return this;
    }

    public Builder description(@NotNull String description) {
      this.description = description;
      return this;
    }

    public BacklogMutation build() {
      Utils.checkNotNull(id, "id == null");
      Utils.checkNotNull(idProject, "idProject == null");
      Utils.checkNotNull(name, "name == null");
      Utils.checkNotNull(status, "status == null");
      Utils.checkNotNull(begindate, "begindate == null");
      Utils.checkNotNull(enddate, "enddate == null");
      Utils.checkNotNull(description, "description == null");
      return new BacklogMutation(id, idProject, name, status, begindate, enddate, description);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final @NotNull String idProject;

    private final @NotNull String name;

    private final @NotNull String status;

    private final @NotNull Date begindate;

    private final @NotNull Date enddate;

    private final @NotNull String description;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id, @NotNull String idProject, @NotNull String name,
        @NotNull String status, @NotNull Date begindate, @NotNull Date enddate,
        @NotNull String description) {
      this.id = id;
      this.idProject = idProject;
      this.name = name;
      this.status = status;
      this.begindate = begindate;
      this.enddate = enddate;
      this.description = description;
      this.valueMap.put("id", id);
      this.valueMap.put("idProject", idProject);
      this.valueMap.put("name", name);
      this.valueMap.put("status", status);
      this.valueMap.put("begindate", begindate);
      this.valueMap.put("enddate", enddate);
      this.valueMap.put("description", description);
    }

    public @NotNull String id() {
      return id;
    }

    public @NotNull String idProject() {
      return idProject;
    }

    public @NotNull String name() {
      return name;
    }

    public @NotNull String status() {
      return status;
    }

    public @NotNull Date begindate() {
      return begindate;
    }

    public @NotNull Date enddate() {
      return enddate;
    }

    public @NotNull String description() {
      return description;
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
          writer.writeString("idProject", idProject);
          writer.writeString("name", name);
          writer.writeString("status", status);
          writer.writeCustom("begindate", CustomType.DATE, begindate);
          writer.writeCustom("enddate", CustomType.DATE, enddate);
          writer.writeString("description", description);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("createBacklog", "createBacklog", new UnmodifiableMapBuilder<String, Object>(7)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .put("idProject", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "idProject")
        .build())
      .put("name", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "name")
        .build())
      .put("status", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "status")
        .build())
      .put("begindate", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "begindate")
        .build())
      .put("enddate", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "enddate")
        .build())
      .put("description", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "description")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateBacklog createBacklog;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateBacklog createBacklog) {
      this.createBacklog = createBacklog;
    }

    public @Nullable CreateBacklog createBacklog() {
      return this.createBacklog;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createBacklog != null ? createBacklog.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createBacklog=" + createBacklog
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
        return ((this.createBacklog == null) ? (that.createBacklog == null) : this.createBacklog.equals(that.createBacklog));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createBacklog == null) ? 0 : createBacklog.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateBacklog.Mapper createBacklogFieldMapper = new CreateBacklog.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateBacklog createBacklog = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateBacklog>() {
          @Override
          public CreateBacklog read(ResponseReader reader) {
            return createBacklogFieldMapper.map(reader);
          }
        });
        return new Data(createBacklog);
      }
    }
  }

  public static class CreateBacklog {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("idProject", "idProject", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("begindate", "begindate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("enddate", "enddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @Nullable String id;

    final @Nullable String idProject;

    final @Nullable String name;

    final @Nullable String status;

    final @Nullable Date begindate;

    final @Nullable Date enddate;

    final @Nullable String description;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public CreateBacklog(@NotNull String __typename, @Nullable String id,
        @Nullable String idProject, @Nullable String name, @Nullable String status,
        @Nullable Date begindate, @Nullable Date enddate, @Nullable String description) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.idProject = idProject;
      this.name = name;
      this.status = status;
      this.begindate = begindate;
      this.enddate = enddate;
      this.description = description;
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable String idProject() {
      return this.idProject;
    }

    public @Nullable String name() {
      return this.name;
    }

    public @Nullable String status() {
      return this.status;
    }

    public @Nullable Date begindate() {
      return this.begindate;
    }

    public @Nullable Date enddate() {
      return this.enddate;
    }

    public @Nullable String description() {
      return this.description;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], idProject);
          writer.writeString($responseFields[3], name);
          writer.writeString($responseFields[4], status);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], begindate);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[6], enddate);
          writer.writeString($responseFields[7], description);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateBacklog{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "idProject=" + idProject + ", "
          + "name=" + name + ", "
          + "status=" + status + ", "
          + "begindate=" + begindate + ", "
          + "enddate=" + enddate + ", "
          + "description=" + description
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateBacklog) {
        CreateBacklog that = (CreateBacklog) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.idProject == null) ? (that.idProject == null) : this.idProject.equals(that.idProject))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.begindate == null) ? (that.begindate == null) : this.begindate.equals(that.begindate))
         && ((this.enddate == null) ? (that.enddate == null) : this.enddate.equals(that.enddate))
         && ((this.description == null) ? (that.description == null) : this.description.equals(that.description));
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
        h ^= (idProject == null) ? 0 : idProject.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (begindate == null) ? 0 : begindate.hashCode();
        h *= 1000003;
        h ^= (enddate == null) ? 0 : enddate.hashCode();
        h *= 1000003;
        h ^= (description == null) ? 0 : description.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<CreateBacklog> {
      @Override
      public CreateBacklog map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String idProject = reader.readString($responseFields[2]);
        final String name = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Date begindate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Date enddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final String description = reader.readString($responseFields[7]);
        return new CreateBacklog(__typename, id, idProject, name, status, begindate, enddate, description);
      }
    }
  }
}