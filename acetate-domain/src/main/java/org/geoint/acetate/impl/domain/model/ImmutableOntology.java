package org.geoint.acetate.impl.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.Ontology;
import org.geoint.acetate.model.common.Version;
import org.geoint.acetate.meta.ModelException;

/**
 *
 */
public class ImmutableOntology implements Ontology {

    private final DomainId domainId;
    private final Collection<DataModel> domains;
    private final Map<String, Collection<ObjectModel>> attributeIndex;
    private final Map<String, ObjectModel> componentIndex; //key unambiguous component name

    private static final Logger logger
            = Logger.getLogger(ImmutableOntology.class.getName());

//    public static ImmutableOntology fromObjects(DomainId domainId,
//            Collection<ObjectModel> allDomainObjects) 
//            throws ModelException {
//        Map<DomainId, Collection<ObjectModel>> models = new HashMap<>();
//        allDomainObjects.forEach((o) -> {
//            DomainId did = DomainId.getInstance(o.getDomainName(), o.getDomainVersion());
//            models.putIfAbsent(did, new ArrayList<>()); //TODO change to method reference
//            models.get(did).add(o);
//        });
//        
//        Collection<DomainModel> domains = new ArrayList<>();
//        
//        return new ImmutableOntology(domainId, models.entrySet().stream()
//                .map((e) -> new ImmutableDomainModel(e.getKey(), e.getValue()))
//                .collect(Collectors.toList())
//        );
//    }
    public ImmutableOntology(DomainId domainId,
            Collection<DataModel> models) {
        this.domainId = domainId;
        this.domains = Collections.unmodifiableCollection(models);

        Map<String, Collection<ObjectModel>> tmpAttIndex = new HashMap<>();
        Map<String, ObjectModel> tmpCompIndex = new HashMap<>();

        models.stream().flatMap((d) -> d.findAll().stream())
                .map((o) -> {
                    //register with component index, if unambiguous
                    final String name = o.getName();
                    if (tmpCompIndex.containsKey(name)) {
                        logger.finest(() -> "Ambiguous component '" + name + "' in "
                                + "ontology '" + domainId.asString() + "'; must "
                                + "be referenced within domain context.");
                        tmpCompIndex.remove(name);
                    } else {
                        tmpCompIndex.put(name, o);
                    }
                    return o;
                })
                .forEach((o) -> {
                    o.getAttributes().keySet().forEach((a) -> {
                        tmpAttIndex.putIfAbsent(a, new ArrayList<>());
                        tmpAttIndex.get(a).add(o);
                    });
                });

        this.attributeIndex = Collections.unmodifiableMap(tmpAttIndex);
        this.componentIndex = Collections.unmodifiableMap(tmpCompIndex);
    }

    @Override
    public String getName() {
        return domainId.getName();
    }

    @Override
    public Version getVersion() {
        return domainId.getVersion();
    }

    @Override
    public Collection<DataModel> getDomains() {
        return domains;
    }

    @Override
    public Collection<ObjectModel> findAll() {
        return domains.stream()
                .flatMap((d) -> d.findAll().stream())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param componentName
     * @return model if unambiguous, null if not present or ambiguous
     */
    @Override
    public Optional<ObjectModel> find(String componentName) {
        return Optional.ofNullable(componentIndex.get(componentName));
    }

    @Override
    public Collection<ObjectModel> findByAttribute(String attributeName) {
        return (attributeIndex.containsKey(attributeName))
                ? attributeIndex.get(attributeName)
                : Collections.EMPTY_LIST;
    }

}
