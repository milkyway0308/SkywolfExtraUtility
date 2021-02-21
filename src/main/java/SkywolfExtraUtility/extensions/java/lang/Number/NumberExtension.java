package SkywolfExtraUtility.extensions.java.lang.Number;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class NumberExtension {
    public static Number plus(@This Number thiz, Number val) {
        return thiz.doubleValue() + val.doubleValue();
    }

    public static Number minus(@This Number thiz, Number val) {
        return thiz.doubleValue() - val.doubleValue();
    }
}