const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}


$.ajaxSetup({
    converters: {
        'text json': function (text) {
            let json = JSON.parse(text);
            if (typeof json === 'object') {
                $(json).each(function () {
                    if (this.hasOwnProperty('dateTime')) {
                        this.dateTime = this.dateTime.replace('T', ' ').substring(0, 16);
                    }
                });
            }
            return json;
        }
    }
});

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess);
            }
        })
    );
});

$.datetimepicker.setLocale(navigator.languages
    ? navigator.languages[0].substring(0, 2)
    : navigator.language.substring(0, 2));

let startDate = $('#startDate');
let endDate = $('#endDate');

startDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            maxDate: endDate.val() ? endDate.val() : false
        })
    }
});

endDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            minDate: startDate.val() ? startDate.val() : false
        })
    }
});

let startTime = $('#startTime');
let endTime = $('#endTime');

startTime.datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            maxTime: endTime.val() ? endTime.val() : false
        })
    }
});

endTime.datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            minTime: startTime.val() ? startTime.val() : false
        })
    }
});

$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});
