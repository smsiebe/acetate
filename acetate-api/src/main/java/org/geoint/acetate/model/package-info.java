/**
 * A data model consists of one or more data components which define the data
 * types (and their model-specific characteristics).
 *
 * A data model simply defines what data components are available for use as
 * data, but does not specify how those components are 
 * {@link DataStructure structured } or how a specific
 * {@link Data data instance} uses the model/structure.
 *
 * A {@link DataModel data model} consists of {@link DataComponent components},
 * often hierarchically nested (as a graph), which are specific
 * {@link DataType data types} optionally {@link DataConstraint constrainted} by
 * rules and/or identified as having special significance to the model (such as
 * being the GUID for the data type, for example). Components defined how a data
 * type is used in the positional context of the data model (where the data type
 * is used in the object graph).
 *
 * The model may include any number of components, and may be associated with
 * structure/data that doesn't make use of all the components within a model. As
 * such, a model instance may include all of the the components within a domain
 * model, or simply the required subset for the operation. At a minimum, a model
 * referenced by a structure or data instance must include all the components
 * defined by that structure/data instance.
 */
@Domain(name = "acetate", version = 1)
package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

