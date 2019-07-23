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

public final class EpicEditMutation implements Mutation<EpicEditMutation.Data, EpicEditMutation.Data, EpicEditMutation.Variables> {
  public static final String OPERATION_ID = "15e2684585cf9b4c9dde4465087f4c9a9b700b29d2e73923e4347c6c7b81e3d8";

  public static final String QUERY_DOCUMENT = "mutation EpicEdit($id: String!, $idProject: String!, $name: String!, $summary: String!, $modifieddate: Date!, $modifiedby: String!) {\n"
      + "  editEpic(id: $id, idProject: $idProject, name: $name, summary: $summary, modifieddate: $modifieddate, modifiedby: $modifiedby) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    idProject\n"
      + "    name\n"
      + "    summary\n"
      + "    modifieddate\n"
      + "    modifiedby\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "EpicEdit";
    }
  };

  private final EpicEditMutation.Variables variables;

  public EpicEditMutation(@NotNull String id, @NotNull String idProject, @NotNull String name,
      @NotNull String summary, @NotNull Date modifieddate, @NotNull String modifiedby) {
    Utils.checkNotNull(id, "id == null");
    Utils.checkNotNull(idProject, "idProject == null");
    Utils.checkNotNull(name, "name == null");
    Utils.checkNotNull(summary, "summary == null");
    Utils.checkNotNull(modifieddate, "modifieddate == null");
    Utils.checkNotNull(modifiedby, "modifiedby == null");
    variables = new EpicEditMutation.Variables(id, idProject, name, summary, modifieddate, modifiedby);
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
  public EpicEditMutation.Data wrapData(EpicEditMutation.Data data) {
    return data;
  }

  @Override
  public EpicEditMutation.Variables variables() {
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

    private @NotNull String summary;

    private @NotNull Date modifieddate;

    private @NotNull String modifiedby;

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

    public Builder summary(@NotNull String summary) {
      this.summary = summary;
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

    public EpicEditMutation build() {
      Utils.checkNotNull(id, "id == null");
      Utils.checkNotNull(idProject, "idProject == null");
      Utils.checkNotNull(name, "name == null");
      Utils.checkNotNull(summary, "summary == null");
      Utils.checkNotNull(modifieddate, "modifieddate == null");
      Utils.checkNotNull(modifiedby, "modifiedby == null");
      return new EpicEditMutation(id, idProject, name, summary, modifieddate, modifiedby);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final @NotNull String idProject;

    private final @NotNull String name;

    private final @NotNull String summary;

    private final @NotNull Date modifieddate;

    private final @NotNull String modifiedby;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id, @NotNull String idProject, @NotNull String name,
        @NotNull String summary, @NotNull Date modifieddate, @NotNull String modifiedby) {
      this.id = id;
      this.idProject = idProject;
      this.name = name;
      this.summary = summary;
      this.modifieddate = modifieddate;
      this.modifiedby = modifiedby;
      this.valueMap.put("id", id);
      this.valueMap.put("idProject", idProject);
      this.valueMap.put("name", name);
      this.valueMap.put("summary", summary);
      this.valueMap.put("modifieddate", modifieddate);
      this.valueMap.put("modifiedby", modifiedby);
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

    public @NotNull String summary() {
      return summary;
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
          writer.writeString("idProject", idProject);
          writer.writeString("name", name);
          writer.writeString("summary", summary);
          writer.writeCustom("modifieddate", CustomType.DATE, modifieddate);
          writer.writeString("modifiedby", modifiedby);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("editEpic", "editEpic", new UnmodifiableMapBuilder<String, Object>(6)
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
      .put("summary", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "summary")
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

    final @Nullable EditEpic editEpic;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable EditEpic editEpic) {
      this.editEpic = editEpic;
    }

    public @Nullable EditEpic editEpic() {
      return this.editEpic;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], editEpic != null ? editEpic.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "editEpic=" + editEpic
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
        return ((this.editEpic == null) ? (that.editEpic == null) : this.editEpic.equals(that.editEpic));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (editEpic == null) ? 0 : editEpic.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final EditEpic.Mapper editEpicFieldMapper = new EditEpic.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final EditEpic editEpic = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<EditEpic>() {
          @Override
          public EditEpic read(ResponseReader reader) {
            return editEpicFieldMapper.map(reader);
          }
        });
        return new Data(editEpic);
      }
    }
  }

  public static class EditEpic {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("idProject", "idProject", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("summary", "summary", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("modifieddate", "modifieddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("modifiedby", "modifiedby", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @Nullable String id;

    final @Nullable String idProject;

    final @Nullable String name;

    final @Nullable String summary;

    final @Nullable Date modifieddate;

    final @Nullable String modifiedby;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public EditEpic(@NotNull String __typename, @Nullable String id, @Nullable String idProject,
        @Nullable String name, @Nullable String summary, @Nullable Date modifieddate,
        @Nullable String modifiedby) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.idProject = idProject;
      this.name = name;
      this.summary = summary;
      this.modifieddate = modifieddate;
      this.modifiedby = modifiedby;
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

    public @Nullable String summary() {
      return this.summary;
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
          writer.writeString($responseFields[2], idProject);
          writer.writeString($responseFields[3], name);
          writer.writeString($responseFields[4], summary);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], modifieddate);
          writer.writeString($responseFields[6], modifiedby);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "EditEpic{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "idProject=" + idProject + ", "
          + "name=" + name + ", "
          + "summary=" + summary + ", "
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
      if (o instanceof EditEpic) {
        EditEpic that = (EditEpic) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.idProject == null) ? (that.idProject == null) : this.idProject.equals(that.idProject))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.summary == null) ? (that.summary == null) : this.summary.equals(that.summary))
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
        h ^= (idProject == null) ? 0 : idProject.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (summary == null) ? 0 : summary.hashCode();
        h *= 1000003;
        h ^= (modifieddate == null) ? 0 : modifieddate.hashCode();
        h *= 1000003;
        h ^= (modifiedby == null) ? 0 : modifiedby.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<EditEpic> {
      @Override
      public EditEpic map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String idProject = reader.readString($responseFields[2]);
        final String name = reader.readString($responseFields[3]);
        final String summary = reader.readString($responseFields[4]);
        final Date modifieddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final String modifiedby = reader.readString($responseFields[6]);
        return new EditEpic(__typename, id, idProject, name, summary, modifieddate, modifiedby);
      }
    }
  }
}