package com.example.guestbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGuestBook is a Querydsl query type for GuestBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuestBook extends EntityPathBase<GuestBook> {

    private static final long serialVersionUID = -2040888172L;

    public static final QGuestBook guestBook = new QGuestBook("guestBook");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> gno = createNumber("gno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QGuestBook(String variable) {
        super(GuestBook.class, forVariable(variable));
    }

    public QGuestBook(Path<? extends GuestBook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuestBook(PathMetadata metadata) {
        super(GuestBook.class, metadata);
    }

}

