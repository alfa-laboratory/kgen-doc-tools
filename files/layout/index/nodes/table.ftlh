<table>
    <#if header??>
    <thead>
    <tr>
        <#list header as headerNode>
            <th ${alignText(modifier, headerNode?index)?no_esc}>${headerNode?no_esc}</th>
        </#list>
    </tr>
    </thead>
    </#if>
    <#list content as contentRow>
    <tbody>
    <tr>
        <#list contentRow as node>
            <td ${alignText(modifier, node?index)?no_esc}>${node?no_esc}</td>
        </#list>
    </tr>
    </tbody>
    </#list>
    <#if footer??>
    <tfoot>
    <tr>
        <#list footer as footerNode>
            <th ${alignText(modifier, footerNode?index)?no_esc}>${footerNode?no_esc}</th>
        </#list>
    </tr>
    </tfoot>
    </#if>
</table>

<#function alignText modifier index>
    <#local elem = modifier[index]>
    <#if elem?? && elem.direction??>
        <#if elem.direction == "Left">
            <#return "style=\"text-align:left\"">
        </#if>
        <#if elem.direction == "Right">
            <#return "style=\"text-align:right\"">
        </#if>
        <#if elem.direction == "Center">
            <#return "style=\"text-align:center\"">
        </#if>
    </#if>
    <#return "">
</#function>