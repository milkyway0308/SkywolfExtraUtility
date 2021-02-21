package skywolf46.extrautility.collections;

import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.Structural;

@Structural
public interface NumericItems {

    @Self Object plus(@Self Object self);

    @Self Object minus(@Self Object self);

}
