package com.rbkmoney.geck.filter.kit.msgpack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused", "Indentation",
        "EmptyLineSeparator",
        "LineLength",
        "MissingSwitchDefault",
        "EmptyBlock",
        "NeedBraces",
        "LeftCurly",
        "LocalVariableName",
        "TypeName",
        "WhitespaceAround",
        "MemberName",
        "ParameterName",
        "NoWhitespaceBefore",
        "ParenPad",
        "AbbreviationAsWordInName",
        "MethodName",
        "ArrayTypeStyle",
        "VariableDeclarationUsageDistance",
        "RightCurlySame",
        "RightCurlyAlone",
        "FallThrough",
        "NoWhitespaceBefore",
        "NonEmptyAtclauseDescription",
        "OverloadMethodsDeclarationOrder",
        "ModifierOrder",
        "OperatorWrap",
})
class SelectorJoiner {
    Map.Entry<Selector, Selector.Context[]> join(List<Map.Entry<Selector, Selector.Context[]>> selectorEntries) {
        List<Selector.Context> contexts = new ArrayList<>(selectorEntries.size());
        List<Map.Entry<Selector, Selector.Context[]>> selectorTracks = selectorEntries.stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue())).collect(Collectors.toList());
        for (Map.Entry<Selector, Selector.Context[]> selectorTrack: selectorTracks) {

        }
        return null;
    }
}
