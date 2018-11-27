class Login extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            msg: ""
        };
    }

    handleSubmit(event) {
        const self = this;
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
            } else {
                self.showAlert(
                    <div className="alert alert-danger">
                        FEIL E-POST ELLER PASSORD!
                    </div>
                )
            }
        });
    }

    showAlert(msg) {
        this.setState({
            msg: msg
        });
        this.intervalID = setInterval(
            () => this.hideAlert(),
            3000
        );
    }

    hideAlert() {
        this.setState({
            msg: ""
        });
        clearInterval(this.intervalID);
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div>
                    {this.state.msg}
                </div>
                <label htmlFor="email" className="sr-only">Email</label>
                <input name="email" type="email" id="email" className="form-control form-input"
                       placeholder="E-post"
                       required autoFocus/>
                <label htmlFor="password" className="sr-only">Password</label>
                <input name="password" type="password" id="password" className="form-control form-input"
                       placeholder="passord" required/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="far fa-arrow-alt-circle-right"/> Logg inn
                </button>
                <div className="row">
                    <div className="col-md-12">
                        <button type="button"
                                className="btn btn-dark registration-btn btn-lg btn-block"
                                onClick={() => { renderReactComponent(NewMember, 'root')}}>
                            Registrer deg
                        </button>
                    </div>
                    <div className="col-md-12">
                        <button type="button" className="btn forgot-pw-btn btn-light btn-lg btn-block"
                                onClick={() => {renderReactComponent(ForgotPassword, 'root')}}>
                            Glemt passord
                        </button>
                    </div>
                </div>
            </form>
        );
    }
}

class NewMember extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            msg: ""
        };
    }

    handleSubmit(event) {
        const self = this;

        event.preventDefault();
        const data = new FormData(event.target);

        const name = data.get('name');
        const email = data.get('email');
        const password = data.get('password');
        const phone = data.get('phone');
        const birth = data.get('birth');

        fetch('/isEmailTaken', {
            method: 'POST',
            body: JSON.stringify({"email": email})
        }).then(function (response) {
            if(response.ok) {
                fetch('/newMember', {
                    method: 'POST',
                    body: JSON.stringify({"name": name, "email": email, "password": password, "phone": phone, "birth": birth}),
                }).then(function (response) {
                    if (response.ok) {
                        fetch('/login', {
                            method: 'POST',
                            body: JSON.stringify({"email": email, "password": password})
                        }).then(function (response) {
                            if (response.ok) {
                                fetch('/sendEmailVerification', {
                                    method: 'POST',
                                    body: JSON.stringify({"email": email})
                                }).then(function (response) {
                                    if (response.ok) {
                                        EmailVerificationEmailSentDialog.email = email;
                                        renderReactComponent(EmailVerificationEmailSentDialog, 'root');
                                    }
                                });
                            }
                        });
                    }
                });
            } else {
                self.showAlert(
                    <div className="alert alert-danger">
                        E-post er allerede i bruk
                    </div>
                )
            }
        });
    }

    showAlert(msg) {
        this.setState({
            msg: msg
        });
        this.intervalID = setInterval(
            () => this.hideAlert(),
            3000
        );
    }

    hideAlert() {
        this.setState({
            msg: ""
        });
        clearInterval(this.intervalID);
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div>
                    {this.state.msg}
                </div>
                <label htmlFor="name" className="sr-only">Email</label>
                <input name="name" type="text" id="name" className="form-control form-input"
                       placeholder="Navn"
                       required autoFocus/>
                <label htmlFor="email" className="sr-only">Email</label>
                <input name="email" type="email" id="email" className="form-control form-input"
                       placeholder="E-post"
                       required/>
                <label htmlFor="password" className="sr-only">Password</label>
                <input name="password" type="password" id="password" className="form-control form-input"
                       placeholder="passord" required/>
                <label htmlFor="phone" className="sr-only">Password</label>
                <input name="phone" type="text" id="phone" className="form-control form-input"
                       placeholder="Tlf.:" required/>
                <label htmlFor="birth" className="sr-only">Password</label>
                <input name="birth" type="text" id="birth" className="form-control form-input"
                       placeholder="Fødselsdato:" required/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-user-plus"/> Opprett bruker
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => { renderReactComponent(Login, 'root') }}>
                    <span className="glyphicon glyphicon-envelope"/>Avbryt
                </button>
            </form>
        );
    }

}

class ForgotPassword extends React.Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const email = data.get('email');

        fetch('/sendPasswordResetEmail', {
            method: 'POST',
            body: JSON.stringify({"email": email}),
        }).then(function (response) {
            if (response.ok) {
                renderReactComponent(ResetPasswordEmailSentDialog, 'root');
            }
        });
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div id="alertMessage">
                </div>
                <label htmlFor="email" className="sr-only">Email</label>
                <input name="email" type="email" id="email" className="form-control form-input"
                       placeholder="E-post"
                       required autoFocus/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-envelope"/> Send
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                            renderReactComponent(Login, 'root')
                        }}>
                    <span className="glyphicon glyphicon-envelope"/>Avbryt
                </button>
            </form>
        );
    }
}

class EmailVerificationEmailSentDialog extends React.Component{

    constructor() {
        super();
        this.email = "";
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div className="alert alert-success">
                    Vi har sendt deg en verifiserings epost til {this.email}, sjekk eposten din!
                </div>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    redirect("member");
                }}>
                    <span className="glyphicon glyphicon-envelope"/>Fortsett
                </button>
            </form>
        );
    }
}

class ResetPasswordEmailSentDialog extends React.Component{

    constructor() {
        super();
        this.email = "";
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="image/åsslogo.png" alt="ÅSS Logo"/>
                <div className="alert alert-success">
                    Vi har sendt deg en verifiserings epost til {this.email}, sjekk eposten din!
                </div>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    redirect("member");
                }}>
                    <span className="glyphicon glyphicon-envelope"/>Fortsett
                </button>
            </form>
        );
    }
}

function renderReactComponent(component, element) {
    ReactDOM.render(React.createElement(component), document.getElementById(element));
}

renderReactComponent(Login, 'root');

