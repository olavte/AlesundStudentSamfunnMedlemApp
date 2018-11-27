class Verification extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const email = data.get('email');

        fetch('/sendNewEmailVerification', {
            method: 'POST',
            body: JSON.stringify({"email": email})
        }).then(function (response) {
            if (response.ok) {
                alert("A new email was sent")
            }
        });
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="image/åsslogo.png" alt="ÅSS Logo"/>
                <label htmlFor="email" className="sr-only">Email</label>
                <div className="alert alert-warning" role="alert">
                    Vennligst verifiser deg ❤️
                </div>
                <input name="email" type="email" id="email" className="form-control form-input"
                       placeholder="E-post"
                       defaultValue={memberObject.email}
                       required autoFocus/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-envelope"/> Send ny verifikasjonsmail
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block"
                        onClick={() => {
                            logout()
                        }}>
                    <span className="fas fa-sign-out-alt"/> Logg ut
                </button>
            </form>
        );
    }
}

class Payment extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const phoneNumber = data.get('phone');
        const discount = data.get('discount');

        fetch('/startVippsPayment', {
            method: 'POST',
            body: JSON.stringify({"phone": phoneNumber, "discount": discount})
        }).then(function (response) {
            return response.json();
        }).then(function (j) {
            let locationJson = JSON.stringify(j);
            let locationObject = JSON.parse(locationJson);

            redirect(locationObject.location);
        });
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="image/åsslogo.png" alt="ÅSS Logo"/>
                <label htmlFor="phone" className="sr-only">Phone</label>
                <div className="alert alert-success" role="alert">
                    Fyll inn ditt mobilnummer så sender vi en regning på vipps ❤️
                </div>
                <input name="phone" type="phone" id="phone" className="form-control form-input"
                       defaultValue={memberObject.phone}
                       placeholder="Tlf.:"
                       required autoFocus/>
                <input name="discount" type="text" id="discount" className="form-control form-input"
                       placeholder="Discount code" autoFocus/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-mobile-alt"></span> Send
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    logout()
                }}>
                    <span className="fas fa-times"></span> Avbryt
                </button>
            </form>
        );
    }
}

class UploadImage extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        const image = document.getElementById("studentCardImage");

        if (image.files && image.files[0]) {
            let reader = new FileReader();
            reader.onload = function (e) {
                fetch('/uploadStudentCard', {
                    method: 'POST',
                    body: JSON.stringify({"idMember": memberObject.idMember, "image": e.target.result}),
                }).then(function (response) {
                    if (response.ok) {
                        renderReactComponent(MemberCard, 'root');
                    }
                });
            };

            reader.readAsDataURL(image.files[0]);
        }
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <label htmlFor="email" className="sr-only">Email</label>
                <div className="alert alert-info" role="alert">
                    Vennligst legg til bilde av studentkortet ditt
                </div>
                <input name="file" enctype="multipart/form-data" type="file" id="studentCardImage"
                       className="form-control form-input text-center"
                       required autoFocus/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-upload"/> Last opp
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    logout()
                }}>
                    <span className="fas fa-times"/> Avbryt
                </button>
            </form>
        );
    }
}

class MemberCard extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            time: new Date().toLocaleString()
        };
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const email = data.get('email');
        const password = data.get('password');

        fetch('/login', {
            method: 'POST',
            body: JSON.stringify({"email": email, "password": password}),
        }).then(function (response) {
            if (response.ok) {
                redirect("member");
            }
        });
    }

    componentDidMount() {
        this.intervalID = setInterval(
            () => this.tick(),
            1000
        );
    }

    componentWillUnmount() {
        clearInterval(this.intervalID);
    }

    tick() {
        this.setState({
            time: new Date().toLocaleString()
        });
    }

    render() {
        return (
            <div>
                <div className="member-card">
                    <div className="col-12 text-center">
                        <img className="logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                    </div>
                    <div className="col-12 text-center">
                        <h1 className="stilling">{memberObject.role}</h1>
                    </div>
                    <div className="col-12 text-center">
                        <h2 className="name-of-member">{memberObject.name}</h2>
                    </div>
                    <div className="col-12 text-center">
                        <h3 className="valid-date">Gyldig til juli 2019</h3>
                    </div>
                    <div className="col-12 text-center">
                        <h5 className="message">
                            Kortet er kun gyldig ved elektronisk validering eller sammen med legitimasjon.
                            Kortet må medbringes for å få tilgang til de rettigheter medlemskapet gir.
                            Mistet kort må straks meldes til ÅSS for sperring.
                        </h5>
                    </div>
                    <div className="row">
                        <div className="text-center col-10 no-padding">
                            <h3 className="time-left">
                                {this.state.time}
                            </h3>
                        </div>
                        <div className="text-left no-padding col">
                            <button className="card-to-menu no-padding" type="button" data-toggle="modal" data-target="#menuModalCenter">
                                <span className="fas fa-sort-up"/>
                            </button>
                        </div>
                        <div className="modal fade" id="menuModalCenter" tabIndex="-1" role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div className="modal-dialog modal-dialog-centered" role="document">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title">Meny</h5>
                                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="row">
                                            <div className="col-sm-12">
                                                <button type="button"
                                                        className="btn btn-secondary btn-block"
                                                        onClick={() => { redirect("/admin") }}>
                                                    <span className="fas fa-lock"/> Admin Panel
                                                </button>
                                            </div>
                                            <div className="col-sm-12">
                                                <button type="button"
                                                        className="btn btn-secondary btn-block popup-menu-button"
                                                        onClick={() => { logout() }}>
                                                    <span className="fas fa-sign-out-alt"/> Logg ut
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
        );
    }
}

function renderReactComponent(component, element) {
    ReactDOM.render(React.createElement(component), document.getElementById(element));
}

function logout() {
    fetch('perform_logout', {
        method: 'GET'
    }).then(function (response) {
        if (response.ok) {
            redirect("/");
        }
    });
}

let memberObject;

fetch('/decodeToken').then(function (response) {
    return response.json();
}).then(function (j) {
    let memberJson = JSON.stringify(j);
    memberObject = JSON.parse(memberJson);

    if (!memberObject.verified) {
        renderReactComponent(Verification, 'root');
    } else {
        fetch('/hasMemberPaid', {
            method: 'POST',
            body: JSON.stringify({"idMember": memberObject.idMember})
        }).then(function (response) {
            if (response.ok) {
                fetch('/hasUploadedStudentCard?idMember=' + memberObject.idMember, {
                    method: 'GET'
                }).then(function (response) {
                    if (response.ok) {
                        renderReactComponent(MemberCard, 'root');
                    } else {
                        renderReactComponent(UploadImage, 'root');
                    }
                });
            } else {
                renderReactComponent(Payment, 'root');
            }
        });
    }
});

