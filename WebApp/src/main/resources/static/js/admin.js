class TopNavBar extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    render() {
        return (
            <nav className="top-navigation navbar navbar-expand navbar-dark bg-dark static-top">
                <a className="navbar-brand mr-1" href="admin.html">ÅSS WEBPANEL</a>
                <button className="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle">
                    <i className="fas fa-bars"/>
                </button>
                <form className="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
                </form>
                <ul className="navbar-nav ml-auto ml-md-0">
                    <li className="nav-item dropdown no-arrow mx-1">
                        <a className="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <i className="fas fa-bell fa-fw"/>
                            <span className="badge badge-danger">9+</span>
                        </a>
                        <div>
                        </div>
                    </li>
                    <li className="nav-item dropdown no-arrow mx-1">
                        <a className="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <i className="fas fa-envelope fa-fw"/>
                            <span className="badge badge-primary">7</span>
                        </a>
                        <div>
                        </div>
                    </li>
                    <li className="nav-item dropdown no-arrow">
                        <a className="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <i className="fas fa-user-circle fa-fw"/>
                        </a>
                        <div className="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                            <a className="dropdown-item disabled" href="#">Messages</a>
                            <a className="dropdown-item disabled" href="#">Preferences</a>
                            <div className="dropdown-divider"/>

                            <a className="dropdown-item" href="#" data-toggle="modal"
                               data-target="#logoutModal">
                                <i className="fas fa-power-off">
                                </i> Logout</a>
                        </div>
                    </li>
                </ul>
            </nav>
        );
    }
}

class SideNavBar extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    render() {
        return (
            <ul className="sidebar navbar-nav">
                <li className="nav-item active dashboard">
                    <a className="nav-link" href="admin.html">
                        <i className="fas fa-fw fa-tachometer-alt"/>
                        <span> Dashboard</span>
                    </a>
                </li>
                <li className="nav-title">Webpanel</li>
                <li className="nav-item dropdown">
                    <a className="nav-link dropdown-toggle" href="#" id="membersDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        <i className="fas fa-users"/>
                        <span> Medlemmer</span>
                    </a>
                    <div className="dropdown-menu sidebar-dropdown-menu" aria-labelledby="membersDropdown">
                        <div className="dropdown-item sidebar-dropdown-item"
                           onClick={() => { renderReactComponent(VerifyMember, 'content-wrapper')}}>
                            <i className="fas fa-user"/>
                            <span> Verifiser medlem</span>
                        </div>
                        <div className="dropdown-item sidebar-dropdown-item"
                            onClick={() => renderReactComponent(NewMember, 'content-wrapper')}>
                            <i className="fas fa-plus"/>
                            <span> Nytt medlem</span>
                        </div>
                        <div className="dropdown-item sidebar-dropdown-item"
                           onClick={() => { renderReactComponent(FindMember, 'content-wrapper')}}>
                            <i className="fas fa-search"/>
                            <span> Finn medlem</span>
                        </div>
                    </div>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href="#">
                        <i className="fas fa-envelope"/>
                        <span> E-post</span>
                    </a>
                </li>
                <li className="nav-title">Administrasjon</li>
                <li className="nav-item">
                    <a className="nav-link" href="#">
                        <i className="fas fa-plus"/>
                        <span> Gi tilgang</span>
                    </a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href="#"
                        onClick={() => renderReactComponent(FindUser, 'content-wrapper')}>
                        <i className="fas fa-search"/>
                        <span> Finn bruker</span>
                    </a>
                </li>
                <li className="nav-title">System</li>
                <li className="nav-item">
                    <a className="nav-link" href="#">
                        <i className="fas fa-laptop"/>
                        <span> Oversikt</span>
                    </a>
                </li>
            </ul>
        );
    }
}

class Overview extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    render() {
        return (
            <div className="container-fluid">
                <ol className="breadcrumb">
                    <li className="breadcrumb-item">
                        <a href="#">Dashboard</a>
                    </li>
                    <li className="breadcrumb-item active">Overview</li>
                </ol>
                <div className="container">
                    <div className="row">
                        <div className="col-xl-3 col-sm6 mb-3">
                            <div className="card text-white bg-primary ">
                                <div className="tiles-heading">
                                    antall medlemmer per idag
                                </div>
                                <div className="card-body tiles-body pb-0 pt-0">
                                    <i className="fas fa-users tiles-icon"/>
                                    <span><h3 className="text-value">1213</h3></span>
                                </div>
                                <div className="tiles-footer card-footer">
                                    rekorden er på 1379 medlemmer
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-3 col-sm6 mb-3">
                            <div className="card text-white bg-primary ">
                                <div className="tiles-heading cyan-header">
                                    antall menn
                                </div>
                                <div className="card-body tiles-body pb-0 pt-0 cyan-body">
                                    <i className="fas fa-male tiles-icon"/>
                                    <span><h3 className="text-value">734</h3></span>
                                </div>
                                <div className="tiles-footer card-footer cyan-footer">
                                    61% av våre medlemmer
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-3 col-sm6 mb-3">
                            <div className="card text-white bg-primary ">
                                <div className="tiles-heading pink-header">
                                    antall kvinner
                                </div>
                                <div className="card-body tiles-body pb-0 pt-0 pink-body">
                                    <i className="fas fa-female tiles-icon"/>
                                    <span><h3 className="text-value">479</h3></span>
                                </div>
                                <div className="tiles-footer card-footer pink-footer">
                                    39% av våre medlemmer
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-3 col-sm6 mb-3">
                            <div className="card text-white bg-primary">
                                <div className="tiles-heading orange-header">
                                    nye medlemmer
                                </div>
                                <div className="card-body tiles-body pb-0 pt-0 orange-body">
                                    <i className="fas fa-arrow-up tiles-icon"/>
                                    <span><h3 className="text-value">2</h3></span>
                                </div>
                                <div className="tiles-footer card-footer orange-footer">
                                    0.16% økning siden 29.oktober 2018
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}


class FindMember extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    render() {
        return (
            <div id="content-wrapper">
                <div className="container-fluid">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="#">Dashboard</a>
                        </li>
                        <li className="breadcrumb-item active">Finn Medlem</li>
                    </ol>
                    <div className="card mb-3">
                        <div className="card-header">
                            <div className="row">
                                <div className="col-md-6 mt-1">
                                    <i className="fas fa-table"/>
                                    <span className="card-title mb-0"> Medlemsliste</span>
                                </div>
                                <div className="col-md-6 pr-1">
                                    <div className="row">
                                        <select className="custom-select col-md-3">
                                            <option>10</option>
                                            <option>25</option>
                                            <option>50</option>
                                            <option>100</option>
                                        </select>
                                        <div className="col-md-8 pr-0">
                                            <input type="search" className="form-control" placeholder="Søk..."
                                                   aria-controls="filter"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="card-body">
                            <div className="table-responsive">
                                <table className="table" id="memberTable" width="100%" cellSpacing="0">
                                    <thead>
                                    <tr>
                                        <th scope="col">Navn</th>
                                        <th scope="col">Kontakt</th>
                                        <th scope="col">
                                            <div className="text-center">
                                                <i className="table-icon fas fa-pencil-alt"/>
                                            </div>
                                        </th>
                                        <th scope="col">
                                            <div className="text-center">
                                                <i className="far fa-credit-card"/>
                                            </div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>Aron Mar Nicholasson</th>
                                        <th>452 02 944</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-primary btn-set-pad">Endre
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    <i className="fas fa-credit-card"/>
                                                    Erstatningskort
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Oliver Tellnes</th>
                                        <th>452 79 562</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-primary btn-set-pad">Endre
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    <i className="fas fa-credit-card"/>
                                                    Erstatningskort
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Olav Telseth</th>
                                        <th>913 60 469</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-primary btn-set-pad">Endre
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    <i className="fas fa-credit-card"/>
                                                    Erstatningskort
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Kjetil Hammerseth</th>
                                        <th>xxx xx xxx</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-primary btn-set-pad">Endre
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    <i className="fas fa-credit-card"/>
                                                    Erstatningskort
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div className="card-footer small">
                            <div className="row">
                                <div className="col-sm-6 mt-2">
                                    Showing 1 to 1 entries
                                </div>
                                <div className="col-sm-6">
                                    <nav aria-label="pagination">
                                        <ul className="pagination mb-0">
                                            <li className="page-item disabled">
                                                <a className="page-link" href="#" tabIndex="-1">Previous</a>
                                            </li>
                                            <li className="page-item active">
                                                <a className="page-link" href="#">1
                                                    <span className="sr-only">(current)</span>
                                                </a>
                                            </li>
                                            <li className="page-item disabled">
                                                <a className="page-link" href="#">Next</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

class FindUser extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    render() {
        return (
            <div id="content-wrapper">
                <div className="container-fluid">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="#">Dashboard</a>
                        </li>
                        <li className="breadcrumb-item active">Finn Medlem</li>
                    </ol>
                    <div className="card mb-3">
                        <div className="card-header">
                            <div className="row">
                                <div className="col-md-6 mt-1">
                                    <i className="fas fa-search"/>
                                    <span className="card-title mb-0"> Finn bruker med tilgang</span>
                                </div>
                                <div className="col-md-6 pr-1">
                                    <div className="row">
                                        <select className="custom-select col-md-3">
                                            <option>10</option>
                                            <option>25</option>
                                            <option selected="selected">50</option>
                                            <option>100</option>
                                        </select>
                                        <div className="col-md-8 pr-0">
                                            <input type="search" className="form-control" placeholder="Søk..."
                                                   aria-controls="filter"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="card-body">
                            <div className="table-responsive">
                                <table className="table" id="memberTable" width="100%" cellSpacing="0">
                                    <thead>
                                    <tr>
                                        <th scope="col">Navn på bruker</th>
                                        <th scope="col">Tlf/e-post</th>
                                        <th scope="col" className="text-center">Verv/komite</th>
                                        <th scope="col" className="text-center">Endre tilgang og verv</th>
                                        <th scope="col" className="text-center">Fjern tilgang og verv</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>Aron Mar Nicholasson</th>
                                        <th>452 02 944</th>
                                        <th className="text-center">IT</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    Endre tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-danger btn-set-pad">
                                                    Fjern tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Oliver Tellnes</th>
                                        <th>452 79 562</th>
                                        <th className="text-center">IT</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    Endre tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-danger btn-set-pad">
                                                    Fjern tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Olav Telseth</th>
                                        <th>913 60 469</th>
                                        <th className="text-center">IT</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    Endre tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-danger btn-set-pad">
                                                    Fjern tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>Kjetil Hammerseth</th>
                                        <th>123 45 678</th>
                                        <th className="text-center">IT</th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-success btn-set-pad">
                                                    Endre tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                        <th>
                                            <div className="text-center">
                                                <button type="button" className="btn btn-danger btn-set-pad">
                                                    Fjern tilgang og verv
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div className="card-footer small">
                            <div className="row">
                                <div className="col-sm-6 mt-2">
                                    Showing 1 to 1 entries
                                </div>
                                <div className="col-sm-6">
                                    <nav aria-label="pagination">
                                        <ul className="pagination mb-0">
                                            <li className="page-item disabled">
                                                <a className="page-link" href="#" tabIndex="-1">Previous</a>
                                            </li>
                                            <li className="page-item active">
                                                <a className="page-link" href="#">1
                                                    <span className="sr-only">(current)</span>
                                                </a>
                                            </li>
                                            <li className="page-item disabled">
                                                <a className="page-link" href="#">Next</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

class VerifyMember extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const file = data.get('file');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"file": file}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    getAllWaitingStudentCards() {
        fetch('/getAllWaitingStudentCards').then(function (response) {
            return response.json();
        }).then(function (j) {
            let memberJson = JSON.stringify(j);
            let image = JSON.parse(memberJson);

            alert(image[0].imageBlob);

            // TODO: Make some kind of logic to be able to iterate trough all the images. Maybe the db should just return a small number or something.

            document.getElementById('studentCardImage').setAttribute(
                'src', image[0].imageBlob
            );
        });
    }

    componentDidMount() {
        this.getAllWaitingStudentCards();
    }

    render() {
        return (
            <div id="content-wrapper">
                <div className="container-fluid">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="#">Dashboard</a>
                        </li>
                        <li className="breadcrumb-item active">Verifiser Medlem</li>
                    </ol>
                    <div className="card mb-3 student-verification">
                        <div className="card-header">
                            VERIFISER MEDLEM
                        </div>
                        <div className="card-body">
                            <div className="">
                                <div className="row student-card-information">
                                    <div className="col-6">
                                        <img className="img-container col-md-6" id="studentCardImage"
                                             src="../image/OliverMedlemskort.png"
                                             alt="Student Card"/>
                                    </div>
                                    <div className="col-6 information">
                                        <label className="mb-0"><h4><b>Navn & Fødselsnummer:</b></h4></label>
                                        <input type="text" className="form-control mb-2"
                                               value="Oliver Mikkelsen Tellnes"/>
                                        <input type="text" className="form-control mb-2" value="13.03.1990"/>
                                        <div className="verified-student text-center">
                                            <h1><b>Gyldig</b></h1>
                                        </div>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-4">
                                        <button type="button" className="btn btn-danger btn-lg btn-block"
                                                data-toggle="modal" data-target="#alertModal">
                                            <b>IKKE GODKJENN</b>
                                        </button>
                                    </div>
                                    <div className="col-8">
                                        <button type="button" className="btn btn-success btn-lg btn-block">
                                            <b>GODKJENN</b>
                                        </button>
                                        <div className="modal fade" id="alertModal" tabIndex="-1" role="dialog"
                                             aria-labelledby="alertModalLabel" aria-hidden="true">
                                            <div className="modal-dialog modal-dialog-centered" role="document">
                                                <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h5 className="modal-title" id="alertModaLabel">Er du helt
                                                            sikker på at du vil avvise dette studentbeviset?</h5>
                                                        <button type="button" className="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div className="modal-body">
                                                        Studenten vil få beskjed på e-post og vil bli bedt om å laste
                                                        opp gyldig studentbevis på nytt
                                                    </div>
                                                    <div className="modal-footer">
                                                        <div className="text-center">
                                                            <button type="button" className="btn btn-danger"
                                                                    data-dismiss="modal"><b>NEI</b></button>
                                                            <button type="button" className="btn btn-success"><b>JA</b>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

class NewMember extends React.Component {

    constructor() {
        super();
    }

    // TODO Add method for handling submit
    // TODO Add method for resetting input data

    render() {
        return (
            <div id="content-wrapper">
                <div className="container-fluid">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="#">Dashboard</a>
                        </li>
                        <li className="breadcrumb-item active">Nytt Medlem</li>
                    </ol>
                    <div className="card mb-3">
                        <div className="card-header">
                            <div className="row">
                                <div className="col-md-6 mt-1">
                                    <span className="card-title mb-0">Fyll ut medlemsinfo</span>
                                </div>
                            </div>
                        </div>
                        <div className="card-body">
                            <form>
                                <div className="form-group">
                                    <label htmlFor="nameInput">Fullt Navn</label>
                                    <input type="text" className="form-control" id="nameInput"
                                           placeholder="Fornavn og etternavn"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="emailInput">E-post</label>
                                    <input type="email" className="form-control" id="emailInput"
                                           placeholder="E-post"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="nasjonalitetInput">Nasjonalitet</label>
                                    <select className="form-control" id="nasjonalitetInput">
                                        <option selected="selected">Norge</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div className="card-footer">
                            <div className="row">
                                <div className="col-md-2"/>
                                <div className="col-md-4">
                                    <button type="button" className="btn btn-danger btn-block btn-lg btn-set-pad">
                                        <i className="fas fa-redo">
                                        </i> Nullstill
                                    </button>
                                </div>
                                <div className="col-md-4">
                                    <button type="button" className="btn btn-success btn-block btn-lg btn-set-pad">
                                        <i className="fas fa-plus-square">
                                        </i> Nytt medlemskort
                                    </button>
                                </div>
                                <div className="col-md-2"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

function renderReactComponent(component, element) {
    ReactDOM.render(React.createElement(component), document.getElementById(element));
}

renderReactComponent(TopNavBar, 'top');
renderReactComponent(SideNavBar, 'side');
renderReactComponent(Overview, 'content-wrapper');