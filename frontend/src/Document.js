import React, { Component } from 'react';
import { Table, Message, Button } from 'semantic-ui-react';
import { Link } from 'react-router';

class DocumentForm extends Component {
  componentDidMount() {
    this.loadDocument();
  }

  constructor(props) {
    super(props);
    this.state = {
      document: null,
      loading: null,
      error: null,
    };
  }
  /**
   *  Загрузка данных о документе
   */
  loadDocument() {
    const { client } = this.props;
    const { documentId } = this.props.router.params;
    this.setState({ document: null, loading: true, error: null });
    client.documents.find({ documentId },
      success => {
        this.setState({ document: success.obj, loading: false });
      },
      error => {
        this.setState({ error, loading: false });
      }
    );
  }

  render() {
    const { document: document_, loading, error } = this.state;
    return (
      <div>
        <div style={{ textAlign: 'right' }}>
          <Link to={`/documents`}>
            <Button icon="left arrow" content="Back" size="mini" compact color="blue"/>
          </Link>
        </div>
        {(!loading && !error) &&
          <DocumentInfo
            document={document_}
          />
        }
        {this.state.error &&
          <Message error>Ошибка запроса документа: {this.state.error}</Message>
        }
      </div>
    );
  }
}
function capitalizeFirstLetter (value) {
    return value ? value.charAt(0).toUpperCase() + value.slice(1) : value;
}

function DocumentInfo (props) {
  const { document } = props;
  if (!document) {
    return (<Message warning>Документ не найден</Message>);
  }
  return (
    <Table striped celled compact size="small">
      <caption>Document</caption>
      <Table.Body>
        {Object.keys(document).map(key => (
          <Table.Row key={key}>
            <Table.Cell>
              {capitalizeFirstLetter(key)}
            </Table.Cell>
            <Table.Cell>
              {document[key]}
            </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table>
  );
}

export default DocumentForm;
