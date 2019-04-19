package com.example.app;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.Query;
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
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import type.CustomType;

@Generated("Apollo GraphQL")
public final class SprintQuery implements Query<SprintQuery.Data, SprintQuery.Data, SprintQuery.Variables> {
  public static final String OPERATION_ID = "114bea0a21ee98c15e5189c47b65bc12b1054e4eebb088cd2c36d004bc8fcbb6";

  public static final String QUERY_DOCUMENT = "query sprint($id: String!) {\n"
      + "  sprint(id: $id) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    begindate\n"
      + "    enddate\n"
      + "    goal\n"
      + "  }\n"
      + "}";

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "sprint";
    }
  };

  private final SprintQuery.Variables variables;

  public SprintQuery(@NotNull String id) {
    Utils.checkNotNull(id, "id == null");
    variables = new SprintQuery.Variables(id);
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
  public SprintQuery.Data wrapData(SprintQuery.Data data) {
    return data;
  }

  @Override
  public SprintQuery.Variables variables() {
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

    Builder() {
    }

    public Builder id(@NotNull String id) {
      this.id = id;
      return this;
    }

    public SprintQuery build() {
      Utils.checkNotNull(id, "id == null");
      return new SprintQuery(id);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @NotNull String id;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@NotNull String id) {
      this.id = id;
      this.valueMap.put("id", id);
    }

    public @NotNull String id() {
      return id;
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
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forList("sprint", "sprint", new UnmodifiableMapBuilder<String, Object>(1)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable List<Sprint> sprint;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable List<Sprint> sprint) {
      this.sprint = sprint;
    }

    public @Nullable List<Sprint> sprint() {
      return this.sprint;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeList($responseFields[0], sprint, new ResponseWriter.ListWriter() {
            @Override
            public void write(List items, ResponseWriter.ListItemWriter listItemWriter) {
              for (Object item : items) {
                listItemWriter.writeObject(((Sprint) item).marshaller());
              }
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "sprint=" + sprint
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
        return ((this.sprint == null) ? (that.sprint == null) : this.sprint.equals(that.sprint));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (sprint == null) ? 0 : sprint.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Sprint.Mapper sprintFieldMapper = new Sprint.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final List<Sprint> sprint = reader.readList($responseFields[0], new ResponseReader.ListReader<Sprint>() {
          @Override
          public Sprint read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Sprint>() {
              @Override
              public Sprint read(ResponseReader reader) {
                return sprintFieldMapper.map(reader);
              }
            });
          }
        });
        return new Data(sprint);
      }
    }
  }

  public static class Sprint {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("begindate", "begindate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("enddate", "enddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("goal", "goal", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @NotNull String id;

    final @Nullable Date begindate;

    final @Nullable Date enddate;

    final @Nullable String goal;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Sprint(@NotNull String __typename, @NotNull String id, @Nullable Date begindate,
        @Nullable Date enddate, @Nullable String goal) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.begindate = begindate;
      this.enddate = enddate;
      this.goal = goal;
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @NotNull String id() {
      return this.id;
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
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], begindate);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], enddate);
          writer.writeString($responseFields[4], goal);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Sprint{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
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
      if (o instanceof Sprint) {
        Sprint that = (Sprint) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
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
        h ^= id.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<Sprint> {
      @Override
      public Sprint map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final Date begindate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final Date enddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String goal = reader.readString($responseFields[4]);
        return new Sprint(__typename, id, begindate, enddate, goal);
      }
    }
  }
}
