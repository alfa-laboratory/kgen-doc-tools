<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#if title??>
         <title>${title}</title>
    <#else>
        <title>documentation example</title>
    </#if>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="${relativePath.value}/static/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${relativePath.value}/static/css/asciidoctor.css">
    <link rel="stylesheet" type="text/css" href="${relativePath.value}/static/css/github.css">
</head>
<body>