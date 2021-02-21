package SkywolfExtraUtility.extensions.java.util.List;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.List;

@Extension
public class ListExtension {
    public static <E> @Self List<E> append(@This List<E> thiz, E target) {
        thiz.add(target);
        return thiz;
    }

    public static <E> List<E> appendUnique(@This List<E> thiz, E target) {
        if (!thiz.contains(target))
            thiz.add(target);
        return thiz;
    }


    public static <E> void addUnique(@This List<E> thiz, E target) {
        thiz.appendUnique(target);
    }
}