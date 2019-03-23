function initAction() {
    var $node = $('.node').off();
    var $exp = $('.exp').off();
    var $label = $('.row,.col').off('mouseenter', 'mouseleave');

    $node.on('dragstart', function (e) {
        e.originalEvent.dataTransfer.setData("code", $(this).attr('data-code'));
    });

    $exp.on('dragover', function (e) {
        e.originalEvent.preventDefault();
    });

    $exp.on('drop', function (e) {
        var code = e.originalEvent.dataTransfer.getData("code");
        var $node = $('.keyword .node[data-code="' + code + '"]');
        $(this).append($node.clone());
    });

    $label.closest('td,th').on('mouseenter', function () {
        $(this).find('.row,.col').show();
    });

    $label.closest('td,th').on('mouseleave', function () {
        $(this).find('.row,.col').hide();
    });
}

function findData(formulaId) {
    if (formulaId == "") {
        showFormula(null);
        return;
    }

    $.ajax({
        url: "/expression/findFormulaInfo",
        type: "GET",
        dataType: "json",
        data: {formulaId: formulaId},
        success: function (response) {
            if (response.errcode == "000000" && response.data) {
                expressionInfoVo = response.data;
                showFormula(response.data);
            }
        }
    });
}

function saveData() {
    $.ajax({
        url: "/expression/saveOrUpdateFormula",
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(getData()),
        success: function (response) {
            if (response.errcode == "000000") {
                alert("保存成功");
                resetFormula();
            } else {
                alert("保存失败");
            }
        }
    });
}

function getData() {
    var $selFormula = $('#formulaSel').find("option:selected");
    var id = $selFormula.attr('data-id');
    var code = $selFormula.attr('data-code');
    var name = $selFormula.text();

    var expressionInfoVo = {
        formula: {
            id: (id ? id : null),
            code: code,
            name: name
        },
        formulaDetails: [],
        tables: []
    };

    $('#formulaDetail tbody tr').each(function (i, v) {
        var digitsStr = emptyToNull($(this).find('.js-digits').val());
        var $nameNode = $(this).find('.js-name .node');
        var $exp = $(this).find('.js-exp');
        expressionInfoVo.formulaDetails.push({
            name: emptyToNull($nameNode.text()),
            code: emptyToNull($nameNode.attr('data-code')),
            exp: emptyToNull(getExp($exp)),
            digits: isNaN(parseInt(digitsStr)) ? null : parseInt(digitsStr)
        });
    });

    $('#formulaTable table').each(function () {
        var table = {
            code: $(this).attr('data-code'),
            name: $(this).find('caption').text(),
            columns: [],
            rows: []
        };

        $(this).find('thead th .node').each(function () {
            table.columns.push({
                code: $(this).attr('data-code'),
                name: $(this).text()
            })
        });

        $(this).find('tbody tr').each(function () {
            var row = [];
            $(this).find('td div.exp').each(function (i, v) {
                row.push(getExp($(v)));
            });
            table.rows.push(row);
        });

        expressionInfoVo.tables.push(table);
    });
    return expressionInfoVo;
}

function resetFormula() {
    findData($("#formulaSel").find("option:selected").attr('data-id'));
}

function showFormula(formula) {
    if (!formula) {
        resetTable();
        return;
    }
    var formulaDetail = "";
    $.each(formula.formulaDetails, function (i, v) {
        topCodeMap[this.code] = [this.name];
        formulaDetail += '<tr>';
        formulaDetail += '<td>' + (i + 1) + '</td>';
        formulaDetail += '<td class="keyword js-name">' + getNode(this.code, this.name, false, true) + '</td>';
        formulaDetail += '<td><div class="exp js-exp" contenteditable="true">' + expToHtml(this.exp, this.code) + '</div></td>';
        formulaDetail += '<td><input class="js-digits" placeholder="小数位" value="' + this.digits + '"></td>';
        formulaDetail += '</tr>';
    });
    $('#formulaDetail tbody').html(formulaDetail);

    $('#formulaTable').empty();
    $.each(formula.tables, function () {
        $('#formulaTable').append(generateTable(this));
    });
    initAction();
}

function getColor(code) {
    if (!code || code.length === 0) {
        return 'rgb(0,0,0)';
    }

    var sum = 0;
    for (var i = 0; i < code.length; i++) {
        sum += code[i].charCodeAt();
    }

    var r = sum % 255;
    var g = sum * 3 % 255;
    var b = sum * 1.5 % 255;
    return 'rgb(' + r + ',' + g + ',' + b + ')';
}

function resetTable() {
    $('#formulaDetail tbody').html("       <tr>" +
        "            <td>1</td>" +
        "            <td><input placeholder=\"名称\"></td>" +
        "            <td><input placeholder=\"编码\"></td>" +
        "            <td><input placeholder=\"默认值\"></td>" +
        "            <td><input placeholder=\"小数位\"></td>" +
        "        </tr>" +
        "        <tr>" +
        "            <td>2</td>" +
        "            <td><input placeholder=\"名称\"></td>" +
        "            <td><input placeholder=\"编码\"></td>" +
        "            <td><input placeholder=\"默认值\"></td>" +
        "            <td><input placeholder=\"小数位\"></td>" +
        "        </tr>" +
        "        <tr>" +
        "            <td>3</td>" +
        "            <td><input placeholder=\"名称\"></td>" +
        "            <td><input placeholder=\"编码\"></td>" +
        "            <td><input placeholder=\"默认值\"></td>" +
        "            <td><input placeholder=\"小数位\"></td>" +
        "        </tr>");
    $("#formulaTable").html("");
}

function randomChar() {
    return String.fromCharCode(65 + Math.random() * 100 % 25);
}

function generateTable(table) {
    var tableStr = "";
    tableStr += '<table class="table rightTable" data-code="' + table.code + '">';
    tableStr += '<caption>' + getNode(table.code, table.name) + '</caption>';
    tableStr += '  <thead>';
    tableStr += '   <tr>';

    $.each(table.columns, function () {
        tableStr += '    <th><div class="exp">' + getNode(this.code, this.name, false) + '</div><span class="col">+</span></th>';
    });
    tableStr += '   </tr>';
    tableStr += '  </thead>';
    tableStr += '  <tbody>';

    $.each(table.rows, function () {
        tableStr += '   <tr>';
        $.each(this, function (index, value) {
            if (index === 0) {
                tableStr += '    <td><div class="exp" contenteditable="true">' + expToHtml(value, value) + '</div><span class="row">+</span></td>';
            } else {
                tableStr += '    <td><div class="exp" contenteditable="true">' + expToHtml(value, value) + '</div></td>';
            }
        });
        tableStr += '   </tr>';
    });
    tableStr += '  </tbody>';
    tableStr += '</table>';

    var $table = $(tableStr);
    var cols = $table.find('thead tr:first-child th').length;
    var rows = $table.find('tr').length;
    var spanArr = $table.find('.row');
    $.each(spanArr, function (index, ele) {
        $(ele).on('click', function () {
            $(this).parents("tr").after(addTRs());
            rows += 1;
            initAction();
        })
    });
    var addTRs = function addTRs() {
        var trs = '';
        trs += '<tr>';
        trs += '<td><div class="exp" contenteditable="true"></div><span class="row">+</span></td>';
        for (var i = 0; i < cols - 1; i++) {
            trs += '<td><div class="exp" contenteditable="true"></div></td>';
        }
        trs += '</tr>';
        var result = $(trs);
        result.find('.row').on('click', function () {
            $(this).parents("tr").after(addTRs());
            rows += 1;
            initAction();
        });
        return result;
    };

    //列生成
    var spanArr2 = $table.find(".col");
    $.each(spanArr2, function (index, ele) {
        $(ele).on('click', function () {
            $(this).parent("th").after(addTH());
            var inx_ = $(this).parent("th").index();
            var tr = $table.find("tbody tr");
            $.each(tr, function () {
                $(this).find("td").eq(inx_).after(addTD());
            });
            cols += 1;
            initAction();
        })
    });

    var addTH = function addTH() {
        var ths = '';
        ths += '<th><div class="exp"></div><span class="col">+</span></th>';
        var result2 = $(ths);
        result2.find('.col').on('click', function () {
            $(this).parent("th").after(addTH());
            var inx_ = $(this).parent("th").index();
            var tr = $table.find("tbody tr");
            $.each(tr, function () {
                $(this).find("td").eq(inx_).after(addTD());
            });
            cols += 1;
        });
        return result2;
    };

    var addTD = function addTD() {
        return '<td><div class="exp" contenteditable="true"></td>';
    };
    return $table;
}

function null2Empty(value) {
    return value ? value : "";
}

function emptyToNull(value) {
    if (value && value.toString().trim() != "") {
        return value.trim();
    }
    return null;
}

function getKeywords(exp, beginChar, endChar) {
    var result = [];
    if (!exp || exp.trim().length === 0) {
        return result;
    }

    var i = 0, j = 0;
    exp = exp.replace(" ", "");
    exp = " " + exp;
    outer:
        while (i != -1 && j != -1) {
            i = exp.indexOf(beginChar, i + 1);
            j = exp.indexOf(endChar, j + 1);
            if (i > 0 && j > 0) {
                var code = exp.substring(i + beginChar.length, j);
                for (var k = 0; k < result.length; k++) {
                    if (result[k] == code) {
                        continue outer;
                    }
                }
                result.push(code);
            }
        }
    return result;
}

function expToHtml(exp, code) {
    var content = null2Empty(exp);
    if (content == "") {
        return "";
    }
    if (code) {
        content = content.replace(/\$\[this.code]/g, code);
    }

    var tags = content.match(/(\${\S+?}|\$\[\S+?]|\(|\)|\+|-|\*|\/|\d+|\S+)/g);
    var result = "";
    $.each(tags, function () {
        if (this.startsWith("${")) {
            var tmp = this.substr(2, this.length - 3);
            var strings = tmp.split(":");
            var tableCode = strings[0];
            var valueCode = strings[2];
            var cons = strings[1].split(",");
            var conName = [];
            $.each(cons, function () {
                var con = this.split("=");
                var key = con[0];
                var value = con[1];
                var columnName = getColumnName(tableCode, key);
                var valueName = expToHtml(value);
                conName.push(columnName + "=" + valueName);
            });
            result += getNode(this, getColumnName(tableCode, valueCode) + '@' + getTableName(tableCode) + '?' + conName.join('&'));
            return true;
        }
        if (this.startsWith("$[")) {
            result += getNode(this.substr(2, this.length - 3));
            return true;
        }
        result += this;
    });
    return result;
}

function getNode(code, name, contentEditable, draggable) {
    contentEditable = contentEditable === true;
    draggable = draggable === true;
    var tmpName = name ? name : topCodeMap[code];

    return '<div class="node" data-code="' + code
        + '" style="background-color: ' + getColor(code)
        + '" contenteditable="' + contentEditable + '" draggable="' + draggable + '">' + tmpName + '</div>';
}

function getTableName(tableCode) {
    var name = "";
    $.each(expressionInfoVo.tables, function () {
        if (this.code == tableCode) {
            name = this.name;
            return false;
        }
    });
    return name;
}

function getColumnName(tableCode, columnCode) {
    var name = "";

    var table = null;
    $.each(expressionInfoVo.tables, function () {
        if (this.code == tableCode) {
            table = this;
            return false;
        }
    });

    if (!table) {
        return name;
    }

    $.each(table.columns, function () {
        if (this.code == columnCode) {
            name = this.name;
            return false;
        }
    });
    return name;
}

function keepLastIndex(obj) {
    if (window.getSelection) {//ie11 10 9 ff safari
        obj.focus(); //解决ff不获取焦点无法定位问题
        var range = window.getSelection();//创建range
        range.selectAllChildren(obj);//range 选择obj下所有子内容
        range.collapseToEnd();//光标移至最后
    }
    else if (document.selection) {//ie10 9 8 7 6 5
        var range = document.selection.createRange();//创建选择对象
        //var range = document.body.createTextRange();
        range.moveToElementText(obj);//range定位到obj
        range.collapse(false);//光标移至最后
        range.select();
    }
}

function getExp($target) {
    var exp = "";
    $target.contents().each(function () {
        if ($(this).hasClass('node')) {
            var code = $(this).attr('data-code');
            if (code.indexOf("$") === -1) {
                code = "$[" + code + "]";
            }
            exp += code;
        } else {
            exp += $(this).text();
        }
    });
    return exp;
}