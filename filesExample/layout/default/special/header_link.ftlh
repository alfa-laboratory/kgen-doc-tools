<li id="#${linkId}"><a href="#${linkId}" class="header-link">${node?no_esc}</a></li>
<#if childs?size != 0 >
    <li><ul>
    <#list childs as child>
        ${child?no_esc}
    </#list>
    </ul></li>
</#if>
