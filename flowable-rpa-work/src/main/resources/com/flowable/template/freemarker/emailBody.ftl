<html>
<head>
    <meta charset="utf-8">
    <title>Notification Email</title>
    <meta name="description" content="notification email">
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            font-family: "Verlag Office", Arial, Sans-serif, serif;
        }

        .warning-red
        {
            color: #B22222FF;
            font-weight: bold;
        }

        .warning-green
        {
            color: #228B22FF;
            font-weight: bold;
        }

        .blank_row
        {
            height: 10px !important;
        }
    </style>
</head>
<body>
<h3>
    Dear <strong>${requester},</strong><br>
</h3>
<p><a>Click</a><a href="http://localhost:8090/#/work/all/case/${url}/tab/workForm/inspect/inspectTab/default"> here</a>!<a> to access your search results!</a>
<#list distinctStores>
    <table>
        <#items as store>
            <tr class="blank_row"><td colspan="6"></td></tr>
            <tr>
                <td><strong>${store}:</strong></td>
            </tr>
            <#list compareList>
                <#items as product>
                    <#if product.store == store>
                        <tr>
                            <td>   .  ${product.name}</td>
                            <td>: </td>
                            <td>${product.price} €</td>
                            <td><strong> &rArr; </strong></td>
                            <#if product.warning>
                                <td class="warning-red"> ${product.priceChange}</td>
                            <#else>
                                <td class="warning-green"> ${product.priceChange}</td>
                            </#if>
                        </tr>
                    </#if>
                </#items>
            </#list>
        </#items>
    </table>
</#list>
</body>
</html>