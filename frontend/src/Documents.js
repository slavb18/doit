import React, { Component } from 'react';
import { Checkbox, Form, Table, Message, Button } from 'semantic-ui-react';
import { Link } from 'react-router';
//import './css/Documents.css';

class FiltrableDocumentsTable extends Component {
  componentDidMount() {
    const { documents, error, loading } = this.state;
    const { client } = this.props;
    if (!documents && !error && !loading && client) {
      this.loadDocuments();
    }
  }

  constructor(props) {
    super(props);
    let query = this.props.location.query;
    this.state = {
      documents: null,
      loading: null,
      error: null,
      filter: {
        displayName: query.displayName ? query.displayName : '',
        showDeleted: query.showDeleted === 'on',
        limit: 100,
      }
    };
  }
  /**
   *  Загрузка данных о документах
   */
  loadDocuments() {
    const { client } = this.props;
    const { filter } = this.state;
    this.setState({ documents: null, loading: true, error: null });
    client.documents.list({ ...filter },
      success => {
        this.setState({ documents: success.obj, loading: false });
      },
      error => {
        this.setState({ error, loading: false });
      }
    );
  };

  removeDocument = (documentId) => {
    const { client } = this.props;
    if (documentId && confirm('Are you sure you want to remove this document?')) {
      client.documents.remove({ documentId },
        success => {
          this.loadDocuments();
        },
        error => {
          alert(`${error.status}: ${error.statusText}`);
        }
      );
    }
  };

  filterOnChangeHandler = (name, value) => {
    const filter = Object.assign(this.state.filter, { [name]: value } );
    this.setState({ filter });
  };

  render() {
    return (
      <div>
        <SearchBar
          filter={this.state.filter}
          onChange={this.filterOnChangeHandler}
        />
        {(this.state.documents) &&
          <DocumentsTable
            documents={this.state.documents}
            removeDocument={this.removeDocument}
            openDocument={this.openDocument}
          />
        }
        {this.state.error &&
          <Message error>Ошибка запроса списка документов: {this.state.error}</Message>
        }

      </div>
    );
  }
}

const SearchBar = (props) => {
  return (
    <Form>
      <Form.Field inline>
        <Form.Input
          type="text"
          name="displayName"
          action='Submit'
          placeholder='Search, may use * and ?...'
          value={props.filter.displayName}
          onChange={(e) => {props.onChange('displayName', e.target.value);}}
        />
        <Checkbox
          name="showDeleted"
          defaultChecked={props.filter.showDeleted}
          label='Show deleted documents'
          onChange={() => {props.onChange('showDeleted', !props.filter.showDeleted);}}
        />
      </Form.Field>
    </Form>
  );
};

function DocumentsTable (props) {
  if (!props.documents.length) {
    return (<Message warning>Документы не найдены</Message>);
  }
  return (
    <Table striped celled selectable compact size="small">
      <caption>List of Documents</caption>
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell collapsing>Id</Table.HeaderCell>
          <Table.HeaderCell collapsing>Date</Table.HeaderCell>
          <Table.HeaderCell>Name</Table.HeaderCell>
          <Table.HeaderCell collapsing/>
        </Table.Row>
      </Table.Header>
      <Table.Body>
        {props.documents.map((document, index) => (
          <Table.Row key={index}>
            <Table.Cell>
              {document.id}
            </Table.Cell>
            <Table.Cell>
              {document.docDate.split('-').reverse().join('.')}
            </Table.Cell>
            <Table.Cell>
              <Link to={`/documents/${document.id}`} activeStyle={{ color: 'red' }}>{document.displayName || '-'}</Link>
            </Table.Cell>
            <Table.Cell>
              <Button icon="remove" color="red" size="mini" fluid
                onClick={props.removeDocument.bind(null, document.id)}
              />
            </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table>
  );
}

export default FiltrableDocumentsTable;
