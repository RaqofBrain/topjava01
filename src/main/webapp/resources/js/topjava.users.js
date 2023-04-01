const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableByData)
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function enable(id, checkBox) {
    let enabled = $(checkBox).is(':checked');
    let row = $('#' + id);
    $.post(ctx.ajaxUrl + id, "enabled=" + enabled)
        .done(function () {
            successNoty(enabled ? "Enabled" : "Disabled");
            row.toggleClass('table-danger', !enabled);
        }).fail(function (jqXHR) {
        failNoty("Oops! Something went wrong! Error status: " + jqXHR.status);
    });
}