package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.DatabaseSequence;

import static java.util.Objects.isNull;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    public static final int INC = 1;
    public static final String ID = "_id";
    public static final String SEQ = "seq";
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where(ID).is(seqName)),
                new Update().inc(SEQ, INC), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !isNull(counter) ? counter.getSeq() : INC;
    }
}
