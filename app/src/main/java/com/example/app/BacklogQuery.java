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
public final class BacklogQuery implements Query<BacklogQuery.Data, BacklogQuery.Data, BacklogQuery.Variables> {
  public static final String OPERATION_ID = "cd662c298de2dca1fc8433825e14f9025bc4121e0ca15afc5977f2dba87a027f";

  public static final String QUERY_DOCUMENT = "query backlog($id: String!) {\n"
      + "  backlog(id: $id) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    idSprint {\n"
      + "      __typename\n"
      + "      id\n"
      + "    }\n"
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
      return "backlog";
    }
  };

  private final BacklogQuery.Variables variables;

  public BacklogQuery(@NotNull String id) {
    Utils.checkNotNull(id, "id == null");
    variables = new BacklogQuery.Variables(id);
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
  public BacklogQuery.Data wrapData(BacklogQuery.Data data) {
    return data;
  }

  @Override
  public BacklogQuery.Variables variables() {
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

    public BacklogQuery build() {
      Utils.checkNotNull(id, "id == null");
      return new BacklogQuery(id);
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
      ResponseField.forList("backlog", "backlog", new UnmodifiableMapBuilder<String, Object>(1)
      .put("id", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "id")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable List<Backlog> backlog;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable List<Backlog> backlog) {
      this.backlog = backlog;
    }

    public @Nullable List<Backlog> backlog() {
      return this.backlog;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeList($responseFields[0], backlog, new ResponseWriter.ListWriter() {
            @Override
            public void write(List items, ResponseWriter.ListItemWriter listItemWriter) {
              for (Object item : items) {
                listItemWriter.writeObject(((Backlog) item).marshaller());
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
          + "backlog=" + backlog
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
        return ((this.backlog == null) ? (that.backlog == null) : this.backlog.equals(that.backlog));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (backlog == null) ? 0 : backlog.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Backlog.Mapper backlogFieldMapper = new Backlog.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final List<Backlog> backlog = reader.readList($responseFields[0], new ResponseReader.ListReader<Backlog>() {
          @Override
          public Backlog read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Backlog>() {
              @Override
              public Backlog read(ResponseReader reader) {
                return backlogFieldMapper.map(reader);
              }
            });
          }
        });
        return new Data(backlog);
      }
    }
  }

  public static class Backlog {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("idSprint", "idSprint", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("begindate", "begindate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("enddate", "enddate", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @NotNull String id;

    final @Nullable IdSprint idSprint;

    final @Nullable String name;

    final @Nullable String status;

    final @Nullable Date begindate;

    final @Nullable Date enddate;

    final @Nullable String description;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Backlog(@NotNull String __typename, @NotNull String id, @Nullable IdSprint idSprint,
        @Nullable String name, @Nullable String status, @Nullable Date begindate,
        @Nullable Date enddate, @Nullable String description) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.idSprint = idSprint;
      this.name = name;
      this.status = status;
      this.begindate = begindate;
      this.enddate = enddate;
      this.description = description;
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @NotNull String id() {
      return this.id;
    }

    public @Nullable IdSprint idSprint() {
      return this.idSprint;
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
          writer.writeObject($responseFields[2], idSprint != null ? idSprint.marshaller() : null);
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
        $toString = "Backlog{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "idSprint=" + idSprint + ", "
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
      if (o instanceof Backlog) {
        Backlog that = (Backlog) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.idSprint == null) ? (that.idSprint == null) : this.idSprint.equals(that.idSprint))
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= (idSprint == null) ? 0 : idSprint.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<Backlog> {
      final IdSprint.Mapper idSprintFieldMapper = new IdSprint.Mapper();

      @Override
      public Backlog map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final IdSprint idSprint = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<IdSprint>() {
          @Override
          public IdSprint read(ResponseReader reader) {
            return idSprintFieldMapper.map(reader);
          }
        });
        final String name = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Date begindate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Date enddate = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final String description = reader.readString($responseFields[7]);
        return new Backlog(__typename, id, idSprint, name, status, begindate, enddate, description);
      }
    }
  }

  public static class IdSprint {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @NotNull String id;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public IdSprint(@NotNull String __typename, @NotNull String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    public @NotNull String id() {
      return this.id;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "IdSprint{"
          + "__typename=" + __typename + ", "
          + "id=" + id
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof IdSprint) {
        IdSprint that = (IdSprint) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id);
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
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<IdSprint> {
      @Override
      public IdSprint map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        return new IdSprint(__typename, id);
      }
    }
  }
}
