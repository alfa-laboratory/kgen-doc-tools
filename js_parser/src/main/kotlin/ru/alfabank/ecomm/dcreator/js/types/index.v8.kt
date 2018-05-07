@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")
@file:JsModule("v8")
package v8

external interface HeapSpaceInfo {
    var space_name: String
    var space_size: Number
    var space_used_size: Number
    var space_available_size: Number
    var physical_space_size: Number
}
external interface HeapInfo {
    var total_heap_size: Number
    var total_heap_size_executable: Number
    var total_physical_size: Number
    var total_available_size: Number
    var used_heap_size: Number
    var heap_size_limit: Number
    var malloced_memory: Number
    var peak_malloced_memory: Number
    var does_zap_garbage: dynamic /* Number /* 0 */ | Number /* 1 */ */
}
external fun getHeapStatistics(): HeapInfo = definedExternally
external fun getHeapSpaceStatistics(): Array<HeapSpaceInfo> = definedExternally
external fun setFlagsFromString(flags: String): Unit = definedExternally
