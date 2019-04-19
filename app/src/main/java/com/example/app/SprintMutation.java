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
public final class SprintMutation implements Mutation<SprintMutation.Data, SprintMutation.Data, SprintMutation.Variables> {
  public static final String OPERATION_ID = "a5582084907438e11b6aed06c56fc70befd55c5def6749e834e99888dd590cae";

  public static final String QUERY_DOCUMENT = "mutation Sprint($id: String!, $idProject: String!, $begindate: Date!, $enddate: Date!, $goal: String!) {\n"
      + "  createSprint(id: $id, idProject: $idProject, begindate: $begindate, enddate: $enddate, goal: $goal) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    idProject\n"
      + "    begindate\n"
      + "    enddate\n"
      + "    goal\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "Sprint";
    }
  };

  private final SprintMutation.Variables variables;

  public SprintMutation(@NotNull String id, @NotNull String idProject, @NotNull Date begindate,
      @NotNull Date enddate, @NotNull String goal) {
    Utils.checkNotNull(id, "id == null");
    Utils.checkNotNull(idProject, "idProject == null");
    Utils.checkNotNull(begindate, "begindate == null");
    Utils.checkNotNull(enddate, "enddate == null");
    Utils.checkNotNull(goal, "goal == null");
    variables = new SprintMutation.Variables(id, idProject, begindate, enddate, goal);
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
  public SprintMutation.Data wrapData(SprintMutation.Data data) {
    return data;
  }

  @Override
  public SprintMutation.Variables variables() {
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

    private @NotNull Date begindate;

    private @NotNull Date enddate;

    private @NotNull String goal;

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

    public Builder begindate(@NotNull Date begindate) {
      this.begindate = begindate;
      return this;
    }

    public Builder enddate(@NotNull Date enddate) {
      this.enddate = enddate;
      return this;
    }

    public Builder goal(@NotNull String goal) {
      this.goal = goal;
      return this;
    }

    public SprintMutation build() {
      Utils.checkNotNull(id, "id == null");
      Utils.checkNotNull(idProject, "idProject == null");
      Utils.checkNotNull(begindate, "begindate == null");
      Utils.checkNotNull(enddate, "enddate == null");
      Utils.checkNotNull(goal, "goal == null");
      return new SprintMutation(id, idProject, begindate, enddate, goal);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final @NotNull String idProject;

    private final @NotNull Date begindate;

    private final @NotNull Date enddate;

    private final @NotNull String goal;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id, @NotNull String idProject, @NotNull Date begindate,
        @NotNull Date enddate, @NotNull String goal) {
      this.id = id;
      this.idProject = idProject;
      this.begindate = begindate;
      this.enddate = enddate;
      this.goal = goal;
      this.valueMap.put("id", id);
      this.valueMap.put("idProject", idProject);
      this.valueMap.put("begindate", begindate);
      this.valueMap.put("enddate", enddate);
      this.valueMap.put("goal", goal);
    }

    public @NotNull String id() {
      return id;
    }

    public @NotNull String idProject() {
      return idProject;
    }

    public @NotNull Date begindate() {
      return begindate;
    }

    public @NotNull Date enddate() {
      return enddate;
    }

    public @NotNull String goal() {
      return goal;
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
          writer.writeCustom("begindate", CustomType.DATE, begindate);
          writer.writeCustom("enddate", CustomType.DATE, enddate);
          writer.writeString("goal", goal);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("createSprint", "createSprint", new UnmodifiableMapBuilder<String, Object>(5)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .put("idProject", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "idProject")
        .build())
      .put("begindate", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "begindate")
        .build())
      .put("enddate", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "enddate")
        .build())
      .put("goal", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "goal")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateSprint createSprint;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateSprint createSprint) {
      this.createSprint = createSprint;
    }

    public @Nullable CreateSprint createSprint() {
      return this.createSprint;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createSprint != null ? createSprint.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createSprint=" + createSprint
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
        return ((this.createSprint == null) ? (that.createSprint == null) : this.createSprint.equals(that.createSprint));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createSprint == null) ? 0 : createSprint.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateSprint.Mapper createSprintFieldMapper = new CreateSprint.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateSprint createSprint = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateSprint>() {
          @Override
          public CreateSprint read(ResponseReader reader) {
            return createSprintFieldMapper.map(reader);
          }
        });
        return new Data(createSprint);
      }
    }
  }

  public static class CreateSprint {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("idProject", "idProject", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("begindate", "begindate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("enddate", "enddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("goal", "goal", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @Nullable String id;

    final @Nullable String idProject;

    final @Nullable Date begindate;

    final @Nullable Date enddate;

    final @Nullable String goal;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public CreateSprint(@NotNull String __typename, @Nullable String id, @Nullable String idProject,
        @Nullable Date begindate, @Nullable Date enddate, @Nullable String goal) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.idProject = idProject;
      this.begindate = begindate;
      this.enddate = enddate;
      this.goal = goal;
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

    public @Nullable Date begindate() {
      return this.begindate;
    }

    public @Nullable Date enddate() {
      return this.enddate;
    }

    public @Nullable String goal() {
      return this.goal;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], idProject);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], begindate);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[4], enddate);
          writer.writeString($responseFields[5], goal);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateSprint{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "idProject=" + idProject + ", "
          + "begindate=" + begindate + ", "
          + "enddate=" + enddate + ", "
          + "goal=" + goal
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateSprint) {
        CreateSprint that = (CreateSprint) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.idProject == null) ? (that.idProject == null) : this.idProject.equals(that.idProject))
         && ((this.begindate == null) ? (that.begindate == null) : this.begindate.equals(that.begindate))
         && ((this.enddate == null) ? (that.enddate == null) : this.enddate.equals(that.enddate))
         && ((this.goal == null) ? (that.goal == null) : this.goal.equals(that.goal));
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
        h ^= (begindate == null) ? 0 : begindate.hashCode();
        h *= 1000003;
        h ^= (enddate == null) ? 0 : enddate.hashCode();
        h *= 1000003;
        h ^= (goal == null) ? 0 : goal.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<CreateSprint> {
      @Override
      public CreateSprint map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String idProject = reader.readString($responseFields[2]);
        final Date begindate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final Date enddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[4]);
        final String goal = reader.readString($responseFields[5]);
        return new CreateSprint(__typename, id, idProject, begindate, enddate, goal);
      }
    }
  }
}
