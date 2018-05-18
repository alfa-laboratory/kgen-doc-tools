<#if level == "ONE">
<h1>${node?no_esc}</h1>
<#elseif level == "TWO">
<h2>${node?no_esc}</h2>
<#elseif level == "THREE">
<h3>${node?no_esc}</h3>
<#elseif level == "FOUR">
<h4>${node?no_esc}</h4>
<#elseif level == "FIVE">
<h5>${node?no_esc}</h5>
</#if>