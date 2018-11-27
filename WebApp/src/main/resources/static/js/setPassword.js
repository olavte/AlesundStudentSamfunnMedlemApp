class SetPassword extends React.Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        const password = data.get('password');

        fetch('/resetPassword', {
            method: 'POST',
            body: JSON.stringify({"password": password}),
        }).then(function (response) {
            if (response.ok) {
                renderReactComponent(PasswordStatus, 'root');
            }
        });
    }

    render() {
        return (
            <form className="form-signin col-12" onSubmit={this.handleSubmit}>
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div className="alert alert-success">
                    Bytt passord!
                </div>
                <input name="password" type="password" id="email" className="form-control form-input"
                       placeholder="Nytt Passord..."
                       required autoFocus/>
                <button type="submit"
                        className="btn btn-primary btn-lg btn-block submit-btn glyphicon glyphicon-send">
                    <span className="fas fa-envelope"/> Bytt passord
                </button>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    redirect("/");
                }}>
                    <span className="glyphicon glyphicon-envelope"/>Avbryt
                </button>
            </form>
        );
    }
}

class PasswordStatus extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <form className="form-signin col-12">
                <img className="mb-4 logo" src="../image/åsslogo.png" alt="ÅSS Logo"/>
                <div className="alert alert-success">
                    Passordet er endret!
                </div>
                <button type="button"
                        className="btn forgot-pw-btn btn-light btn-lg btn-block" onClick={() => {
                    redirect("/");
                }}>
                    <span className="glyphicon glyphicon-envelope"/>Tilbake til login
                </button>
            </form>
        );
    }
}

function renderReactComponent(component, element) {
    ReactDOM.render(React.createElement(component), document.getElementById(element));
}

renderReactComponent(SetPassword, 'root');