import React, { Component } from 'react';
//import logo from './logo.svg';
import { Menu, Container } from 'semantic-ui-react';
import './App.css';
import { Link } from 'react-router';
import classnames from 'classnames';
import Swagger from 'swagger-client';

class App extends Component {
  state = {
    client: null,
    loading: false,
    error: null,
  };

  componentDidMount() {
    const { client, error, loading } = this.state;
    if (!client && !error && !loading) {
      this.createClient();
    }
  }

  createClient = () => {
    this.setState({ client: null, loading: true, error: null });
    const client = new Swagger({
      url: '/doit/web/swagger.json',
      success: () => {
        this.setState({ client, loading: false });
      },
      failure: (error) => {
        this.setState({ error, loading: false });
      },
    });
  }

  render() {
    const { client, error, loading } = this.state;
    return (
      <div className="App">
        <Menu>
          <Menu.Item>
            {/* <img className="ui image" alt="BBLogo" src='http://www.bystrobank.ru/img/logo.gif' /> */}
          </Menu.Item>

          <Link to="/"
            className={classnames('item', { active:  window.location.pathname === '/'})}
          >
            Home
          </Link>

          <Link to="/documents"
            className={classnames('item', { active:  window.location.pathname === '/documents'})}
          >
            Documents
          </Link>
        </Menu>

        <Container>
          {error && <span>Load client error: {error}</span>}
          {loading && <span>Loading...</span>}
          {(client && !loading && !error && this.props.children) && React.cloneElement(this.props.children, { client })}
        </Container>
      </div>
    );
  }
}

export default App;
