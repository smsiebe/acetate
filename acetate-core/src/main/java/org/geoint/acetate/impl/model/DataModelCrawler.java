package org.geoint.acetate.impl.model;

import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.ComposedModel;
import org.geoint.acetate.model.ContextualModel;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelVisitor;
import org.geoint.acetate.model.EntityModel;
import org.geoint.acetate.model.EventModel;
import org.geoint.acetate.model.ModelVisitorResult;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.ValueModel;

/**
 * Crawls the hierarchical data model, notifying a
 * {@link ModelVisitor visitor} about data node.
 *
 * Crawler is implemented as a Runnable so it may not only be managed
 * {@link ModelVisitorResult through the listener}, but also easily managed
 * (stopped/canceled) {@link Future externally}.
 */
public class DataModelCrawler implements Runnable {

    private final ModelVisitor visitor;
    private final DataModel model;

    private static final Logger logger
            = Logger.getLogger(DataModelCrawler.class.getName());

    public DataModelCrawler(ModelVisitor visitor, DataModel model) {
        this.visitor = visitor;
        this.model = model;
    }

    @Override
    public void run() {
        
    }
    
    private ModelVisitorResult visit (DataModel model, ModelVisitorResult previousResult) {
        
        if (previousResult.equals(ModelVisitorResult.SKIP)) {
            logger.finest(() -> "Skipping model component '"+model.toString());
        }
        
        final ModelVisitorResult result;
        if (model instanceof ValueModel) {
            return visitor.value((ValueModel) model);
        } else if (model instanceof ObjectModel) {
            
            visitComposed(model);
        } else if (model instanceof EventModel) {
            visit((EventModel) model);
        } else if (model instanceof EntityModel) {
            visit((EntityModel) model);
        } else {
            logger.log(Level.SEVERE,
                    "Unknown data model component {0}; component will not "
                    + "be visited.",
                    model.getClass().getName());
        }
    }

    private ModelVisitorResult value(ValueModel<?> model) {
        return ;
    }

    private ModelVisitorResult composite(ComposedModel<?> context, ContextualModel model) {
        final ModelVisitorResult result 
                = visitor.composite(context, model.getName(), model);
        if (!result.equals(ModelVisitorResult.CONTINUE)) {
            return result;
        }
    }

    private ModelVisitorResult object(ObjectModel<?> model) {
        final ModelVisitorResult result = visitor.object(model);
        if (result.equals(ModelVisitorResult.CONTINUE)) {
            for (ContextualModel c : model.getComposites()) {
                if (composite(c).equals(ModelVisitorResult.STOP)) {
                    return ModelVisitorResult.STOP;
                }
            }
        }
        return result;
    }

    private ModelVisitorResult event(EventModel<?> model) {

    }

    private ModelVisitorResult entity(EntityModel<?> model) {

    }

    private <M extends DataModel> ModelVisitorResult doVisit(
            BiFunction<ModelVisitor, M, ModelVisitorResult> visitFunc) {

    }
}
