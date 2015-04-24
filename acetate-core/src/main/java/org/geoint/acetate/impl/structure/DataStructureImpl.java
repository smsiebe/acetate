package org.geoint.acetate.impl.structure;

import java.util.Map;
import java.util.Map.Entry;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.structure.DataStructure;
import org.geoint.acetate.structure.DataStructureVisitor;

/**
 * Simple immutable data structure.
 *
 * Thread safe.
 */
public class DataStructureImpl implements DataStructure {

    //source data model
    private final DataModel model;
    //key is relative structural node, component hierarchy is done through 
    //object graph, so this data structure instance is the root node
    private final Map<String, StructureNode> nodes;

    private DataStructureImpl(DataModel model,
            Map<String, StructureNode> nodes) {
        this.model = model;
        this.nodes = nodes;
    }

    @Override
    public DataModel getModel() {
        return model;
    }

    @Override
    public void visit(DataStructureVisitor visitor) {
        //hierarchically visit the nodes using the current sort of each of the 
        //nodes map
        visit("", nodes, visitor);
    }

    private void visit(String currentPosition, Map<String, StructureNode> nodes,
            DataStructureVisitor visitor) {
        for (Entry<String, StructureNode> e : nodes.entrySet()) {
            final String position = currentPosition + "." + e.getKey();
            final StructureNode n = e.getValue();

            //notify the correct visitor callback
            if (n.getClass().equals(InstanceStructureNode.class)) {
                visitor.component(position, ((InstanceStructureNode) n).getComponent());
            } else if (n.getClass().equals(ArrayStructureNode.class)) {
                visitor.array(position, ((ArrayStructureNode) n).getComponent());
            } else if (n.getClass().equals(MapStructureNode.class)) {
                visitor.map(position, ((MapStructureNode) n).getKeyComponent(),
                        ((MapStructureNode) n).getValueComponent());
            }

            //if there are children, descend recursively
            if (!n.children.isEmpty()) {
                visit(position, n.children, visitor);
            }
        }
    }

    /**
     * A wrapper which defines the type of structural component (instance,
     * array, map) the data model supports for that position.
     */
    private static abstract class StructureNode {

        private final Map<String, StructureNode> children;

        public StructureNode(Map<String, StructureNode> children) {
            this.children = children;
        }

    }

    /**
     * Node which defines the data component as supporting at most a single data
     * instance in any data implementation.
     *
     * @param <T>
     */
    private static class InstanceStructureNode<T> extends StructureNode {

        private final StructureComponent<T> component;

        public InstanceStructureNode(StructureComponent<T> component,
                Map<String, StructureNode> children) {
            super(children);
            this.component = component;
        }

        public StructureComponent<T> getComponent() {
            return component;
        }
    }

    /**
     * Node which supports zero to many data instances for the defined
     * component.
     *
     * @param <T>
     */
    private static class ArrayStructureNode<T> extends StructureNode {

        private final StructureComponent<T> component;

        public ArrayStructureNode(StructureComponent<T> component,
                Map<String, StructureNode> children) {
            super(children);
            this.component = component;
        }

        public StructureComponent<T> getComponent() {
            return component;
        }
    }

    /**
     * Node which supports zero to many key/value component pairs at the
     * structural position.
     *
     * @param <K>
     * @param <V>
     */
    private static class MapStructureNode<K, V> extends StructureNode {

        private final StructureComponent<K> keyComponent;
        private final StructureComponent<V> valueComponent;

        public MapStructureNode(StructureComponent<K> keyComponent,
                StructureComponent<V> valueComponent,
                Map<String, StructureNode> children) {
            super(children);
            this.keyComponent = keyComponent;
            this.valueComponent = valueComponent;
        }

        public StructureComponent<K> getKeyComponent() {
            return keyComponent;
        }

        public StructureComponent<V> getValueComponent() {
            return valueComponent;
        }

    }
}
