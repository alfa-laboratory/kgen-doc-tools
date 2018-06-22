<#include "header.ftlh">
<div class="container">
    <div class="data-column" id="data-column">
        <div class="content">
        ${data?no_esc}
        </div>
        <div class="content">
        ${files?no_esc}
        </div>
    </div>
</div>

<script src="${relativePath.value}/static/js/gumshoe.js"></script>
<script src="${relativePath.value}/static/js/index.js"></script>

<script src="${relativePath.value}/static/js/highlight.pack.js"></script>
<script type="text/javascript">
    hljs.initHighlightingOnLoad();
</script>

<#include "footer.ftlh">