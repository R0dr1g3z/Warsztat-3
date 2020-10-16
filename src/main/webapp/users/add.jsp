<%@include file="/header.jsp"%>
<div class="container-fluid">
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
    <a href="<c:url value="/user/add"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
        <i class="fas fa-download fa-sm text-white-50"></i>Lista uzytkownikow</a>
</div>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Dodaj uzytkownika</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table">

                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="/footer.jsp"%>