package io.github.depromeet.knockknockbackend.domain.group.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QGroup is a Querydsl query type for Group */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = -686918346L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroup group = new QGroup("group1");

    public final io.github.depromeet.knockknockbackend.global.database.QBaseTimeEntity _super =
            new io.github.depromeet.knockknockbackend.global.database.QBaseTimeEntity(this);

    public final StringPath backgroundImagePath = createString("backgroundImagePath");

    public final QCategory category;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final EnumPath<GroupType> groupType = createEnum("groupType", GroupType.class);

    public final QGroupUsers groupUsers;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<
                    io.github.depromeet.knockknockbackend.domain.notification.domain.Notification,
                    io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification>
            notifications =
                    this
                            .<io.github.depromeet.knockknockbackend.domain.notification.domain
                                            .Notification,
                                    io.github.depromeet.knockknockbackend.domain.notification.domain
                                            .QNotification>
                                    createList(
                                            "notifications",
                                            io.github.depromeet.knockknockbackend.domain
                                                    .notification.domain.Notification.class,
                                            io.github.depromeet.knockknockbackend.domain
                                                    .notification.domain.QNotification.class,
                                            PathInits.DIRECT2);

    public final BooleanPath publicAccess = createBoolean("publicAccess");

    public final StringPath thumbnailPath = createString("thumbnailPath");

    public final StringPath title = createString("title");

    public QGroup(String variable) {
        this(Group.class, forVariable(variable), INITS);
    }

    public QGroup(Path<? extends Group> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroup(PathMetadata metadata, PathInits inits) {
        this(Group.class, metadata, inits);
    }

    public QGroup(Class<? extends Group> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category =
                inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.groupUsers =
                inits.isInitialized("groupUsers")
                        ? new QGroupUsers(forProperty("groupUsers"))
                        : null;
    }
}
