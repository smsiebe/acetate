package org.geoint.acetate.model;

import java.time.ZonedDateTime;

/**
 * Visitor callback used to {@link DataModel#visit(DataModelVisitor) visit the
 * domain model components}.
 */
public interface ModelVisitor {

    /**
     * Called for a value component model.
     *
     * @param model
     * @return instructs visitor behavior
     */
    ModelVisitorResult value(ValueModel<?> model);

    /**
     * Called before the composite components of an EntityModel are visited.
     *
     * All components visited between calling this method and 
     * {@link #endEntity(EntityModel) }
     *
     * @param model
     * @return instructs visitor behavior
     */
    ModelVisitorResult entity(EntityModel<?> model);

    /**
     * Called before the composite components of an ObjectModel are visited.
     *
     * @param model
     * @return instructs visitor behavior
     */
    ModelVisitorResult object(ObjectModel<?> model);

    /**
     * Called before the composite components of an EventModel are visited.
     *
     * @param model
     * @return instructs visitor behavior
     */
    ModelVisitorResult event(EventModel<?> model);

    /**
     * Called before a composite component
     *
     * @param containerModel
     * @param compositeName
     * @param compositeModel
     * @return
     */
    ModelVisitorResult composite(ComposedModel<?> containerModel, String compositeName,
            ContextualModel compositeModel);

    ModelVisitorResult startCollection();

    ModelVisitorResult endCollection();

    ModelVisitorResult startMap();

    ModelVisitorResult endMap();

    ModelVisitorResult entityGuid(EntityModel<?> entityContext, String componentName,
            ValueModel<String> guidModel);

    ModelVisitorResult entityVersion(EntityModel<?> entityContext, String componentName,
            ValueModel<Long> versionModel);

    ModelVisitorResult eventCommitTime(EventModel<?> eventContext, String componentName,
            ValueModel<ZonedDateTime> commitModel);

    ModelVisitorResult operation(ObjectModel<?> containerModel, String operationName,
            OperationModel model);

}
