package com.example.app;// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//

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

import type.CustomType;

public final class BacklogEditMutation implements Mutation<BacklogEditMutation.Data, BacklogEditMutation.Data, BacklogEditMutation.Variables> {
  public static final String OPERATION_ID = "25ea5f2e11757a63803d9731dd52ebe01d51194621ffdcbd366212c30e51d0b9";

  public static final String QUERY_DOCUMENT = "mutation BacklogEdit($id: String!, $idEpic: String!, $idSprint: String!, $assignee: String!, $name: String!, $status: String!, $description: String!, $modifieddate: Date!, $modifiedby: String!) {\n"
      + "  editBacklog(id: $id, idEpic: $idEpic, idSprint: $idSprint, assignee: $assignee, name: $name, status: $status, description: $description, modifieddate: $modifieddate, modifiedby: $modifiedby) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    idEpic\n"
      + "    idSprint\n"
      + "    assignee\n"
      + "    name\n"
      + "    status\n"
      + "    description\n"
      + "    modifieddate\n"
      + "    modifiedby\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "BacklogEdit";
    }
  };

  private final BacklogEditMutation.Variables variables;

  public BacklogEditMutation(@NotNull String id, @NotNull String idEpic, @NotNull String idSprint,
      @NotNull String assignee, @NotNull String name, @NotNull String status,
      @NotNull String description, @NotNull Date modifieddate, @NotNull String modifiedby) {
    Utils.checkNotNull(id, "id == null");
    Utils.checkNotNull(idEpic, "idEpic == null");
    Utils.checkNotNull(idSprint, "idSprint == null");
    Utils.checkNotNull(assignee, "assignee == null");
    Utils.checkNotNull(name, "name == null");
    Utils.checkNotNull(status, "status == null");
    Utils.checkNotNull(description, "description == null");
    Utils.checkNotNull(modifieddate, "modifieddate == null");
    Utils.checkNotNull(modifiedby, "modifiedby == null");
    variables = new BacklogEditMutation.Variables(id, idEpic, idSprint, assignee, name, status, description, modifieddate, modifiedby);
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
  public BacklogEditMutation.Data wrapData(BacklogEditMutation.Data data) {
    return data;
  }

  @Override
  public BacklogEditMutation.Variables variables() {
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

    private @NotNull String idEpic;

    private @NotNull String idSprint;

    private @NotNull String assignee;

    private @NotNull String name;

    private @NotNull String status;

    private @NotNull String description;

    private @NotNull Date modifieddate;

    private @NotNull String modifiedby;

    Builder() {
    }

    public Builder id(@NotNull String id) {
      this.id = id;
      return this;
    }

    public Builder idEpic(@NotNull String idEpic) {
      this.idEpic = idEpic;
      return this;
    }

    public Builder idSprint(@NotNull String idSprint) {
      this.idSprint = idSprint;
      return this;
    }

    public Builder assignee(@NotNull String assignee) {
      this.assignee = assignee;
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

    public Builder description(@NotNull String description) {
      this.description = description;
      return this;
    }

    public Builder modifieddate(@NotNull Date modifieddate) {
      this.modifieddate = modifieddate;
      return this;
    }

    public Builder modifiedby(@NotNull String modifiedby) {
      this.modifiedby = modifiedby;
      return this;
    }

    public BacklogEditMutation build() {
      Utils.checkNotNull(id, "id == null");
      Utils.checkNotNull(idEpic, "idEpic == null");
      Utils.checkNotNull(idSprint, "idSprint == null");
      Utils.checkNotNull(assignee, "assignee == null");
      Utils.checkNotNull(name, "name == null");
      Utils.checkNotNull(status, "status == null");
      Utils.checkNotNull(description, "description == null");
      Utils.checkNotNull(modifieddate, "modifieddate == null");
      Utils.checkNotNull(modifiedby, "modifiedby == null");
      return new BacklogEditMutation(id, idEpic, idSprint, assignee, name, status, description, modifieddate, modifiedby);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final @NotNull String idEpic;

    private final @NotNull String idSprint;

    private final @NotNull String assignee;

    private final @NotNull String name;

    private final @NotNull String status;

    private final @NotNull String description;

    private final @NotNull Date modifieddate;

    private final @NotNull String modifiedby;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id, @NotNull String idEpic, @NotNull String idSprint,
        @NotNull String assignee, @NotNull String name, @NotNull String status,
        @NotNull String description, @NotNull Date modifieddate, @NotNull String modifiedby) {
      this.id = id;
      this.idEpic = idEpic;
      this.idSprint = idSprint;
      this.assignee = assignee;
      this.name = name;
      this.status = status;
      this.description = description;
      this.modifieddate = modifieddate;
      this.modifiedby = modifiedby;
      this.valueMap.put("id", id);
      this.valueMap.put("idEpic", idEpic);
      this.valueMap.put("idSprint", idSprint);
      this.valueMap.put("assignee", assignee);
      this.valueMap.put("name", name);
      this.valueMap.put("status", status);
      this.valueMap.put("description", description);
      this.valueMap.put("modifieddate", modifieddate);
      this.valueMap.put("modifiedby", modifiedby);
    }

    public @NotNull String id() {
      return id;
    }

    public @NotNull String idEpic() {
      return idEpic;
    }

    public @NotNull String idSprint() {
      return idSprint;
    }

    public @NotNull String assignee() {
      return assignee;
    }

    public @NotNull String name() {
      return name;
    }

    public @NotNull String status() {
      return status;
    }

    public @NotNull String description() {
      return description;
    }

    public @NotNull Date modifieddate() {
      return modifieddate;
    }

    public @NotNull String modifiedby() {
      return modifiedby;
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
          writer.writeString("idEpic", idEpic);
          writer.writeString("idSprint", idSprint);
          writer.writeString("assignee", assignee);
          writer.writeString("name", name);
          writer.writeString("status", status);
          writer.writeString("description", description);
          writer.writeCustom("modifieddate", CustomType.DATE, modifieddate);
          writer.writeString("modifiedby", modifiedby);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("editBacklog", "editBacklog", new UnmodifiableMapBuilder<String, Object>(9)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .put("idEpic", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "idEpic")
        .build())
      .put("idSprint", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "idSprint")
        .build())
      .put("assignee", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "assignee")
        .build())
      .put("name", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "name")
        .build())
      .put("status", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "status")
        .build())
      .put("description", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "description")
        .build())
      .put("modifieddate", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "modifieddate")
        .build())
      .put("modifiedby", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "modifiedby")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable EditBacklog editBacklog;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable EditBacklog editBacklog) {
      this.editBacklog = editBacklog;
    }

    public @Nullable EditBacklog editBacklog() {
      return this.editBacklog;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], editBacklog != null ? editBacklog.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "editBacklog=" + editBacklog
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
        return ((this.editBacklog == null) ? (that.editBacklog == null) : this.editBacklog.equals(that.editBacklog));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (editBacklog == null) ? 0 : editBacklog.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final EditBacklog.Mapper editBacklogFieldMapper = new EditBacklog.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final EditBacklog editBacklog = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<EditBacklog>() {
          @Override
          public EditBacklog read(ResponseReader reader) {
            return editBacklogFieldMapper.map(reader);
          }
        });
        return new Data(editBacklog);
      }
    }
  }

  public static class EditBacklog {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("idEpic", "idEpic", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("idSprint", "idSprint", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("assignee", "assignee", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("modifieddate", "modifieddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("modifiedby", "modifiedby", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @Nullable String id;

    final @Nullable String idEpic;

    final @Nullable String idSprint;

    final @Nullable String assignee;

    final @Nullable String name;

    final @Nullable String status;

    final @Nullable String description;

    final @Nullable Date modifieddate;

    final @Nullable String modifiedby;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public EditBacklog(@NotNull String __typename, @Nullable String id, @Nullable String idEpic,
        @Nullable String idSprint, @Nullable String assignee, @Nullable String name,
        @Nullable String status, @Nullable String description, @Nullable Date modifieddate,
        @Nullable String modifiedby) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.idEpic = idEpic;
      this.idSprint = idSprint;
      this.assignee = assignee;
      this.name = name;
      this.status = status;
      this.description = description;
      this.modifieddate = modifieddate;
      this.modifiedby = modifiedby;
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable String idEpic() {
      return this.idEpic;
    }

    public @Nullable String idSprint() {
      return this.idSprint;
    }

    public @Nullable String assignee() {
      return this.assignee;
    }

    public @Nullable String name() {
      return this.name;
    }

    public @Nullable String status() {
      return this.status;
    }

    public @Nullable String description() {
      return this.description;
    }

    public @Nullable Date modifieddate() {
      return this.modifieddate;
    }

    public @Nullable String modifiedby() {
      return this.modifiedby;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], idEpic);
          writer.writeString($responseFields[3], idSprint);
          writer.writeString($responseFields[4], assignee);
          writer.writeString($responseFields[5], name);
          writer.writeString($responseFields[6], status);
          writer.writeString($responseFields[7], description);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[8], modifieddate);
          writer.writeString($responseFields[9], modifiedby);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "EditBacklog{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "idEpic=" + idEpic + ", "
          + "idSprint=" + idSprint + ", "
          + "assignee=" + assignee + ", "
          + "name=" + name + ", "
          + "status=" + status + ", "
          + "description=" + description + ", "
          + "modifieddate=" + modifieddate + ", "
          + "modifiedby=" + modifiedby
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof EditBacklog) {
        EditBacklog that = (EditBacklog) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.idEpic == null) ? (that.idEpic == null) : this.idEpic.equals(that.idEpic))
         && ((this.idSprint == null) ? (that.idSprint == null) : this.idSprint.equals(that.idSprint))
         && ((this.assignee == null) ? (that.assignee == null) : this.assignee.equals(that.assignee))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.description == null) ? (that.description == null) : this.description.equals(that.description))
         && ((this.modifieddate == null) ? (that.modifieddate == null) : this.modifieddate.equals(that.modifieddate))
         && ((this.modifiedby == null) ? (that.modifiedby == null) : this.modifiedby.equals(that.modifiedby));
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
        h ^= (idEpic == null) ? 0 : idEpic.hashCode();
        h *= 1000003;
        h ^= (idSprint == null) ? 0 : idSprint.hashCode();
        h *= 1000003;
        h ^= (assignee == null) ? 0 : assignee.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (description == null) ? 0 : description.hashCode();
        h *= 1000003;
        h ^= (modifieddate == null) ? 0 : modifieddate.hashCode();
        h *= 1000003;
        h ^= (modifiedby == null) ? 0 : modifiedby.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<EditBacklog> {
      @Override
      public EditBacklog map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String idEpic = reader.readString($responseFields[2]);
        final String idSprint = reader.readString($responseFields[3]);
        final String assignee = reader.readString($responseFields[4]);
        final String name = reader.readString($responseFields[5]);
        final String status = reader.readString($responseFields[6]);
        final String description = reader.readString($responseFields[7]);
        final Date modifieddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[8]);
        final String modifiedby = reader.readString($responseFields[9]);
        return new EditBacklog(__typename, id, idEpic, idSprint, assignee, name, status, description, modifieddate, modifiedby);
      }
    }
  }
}