import React, { Component } from 'react';
import { reduxForm } from 'redux-form';
import { createCase, addCase, updateCase, selectCase } from '../actions.jsx'
import { withRouter } from 'react-router'
import { selectCaseById } from '../reducers.jsx'

class EditCase extends Component {

  componentWillMount() {
    this.props.dispatch(this.props.params.caseId ? selectCase(this.props.params.caseId) : createCase())
  }

  saveCase(caseToSave) {
    this.props.dispatch(this.props.params.caseId ? updateCase(caseToSave) : addCase(caseToSave))
    this.props.router.push('/cases')
  }

  render() {
    const {fields: {id, staffName, status, objective, dateOpened}, handleSubmit} = this.props

    var heading = <h1>New Case</h1>;
    if (this.props.params.caseId) {
      heading = <h1>Edit Case</h1>;
    }
    return (
      <div className="container">
        <form onSubmit={handleSubmit(this.saveCase.bind(this))}>
          {heading}
          <fieldset>
            <legend>
              Family First Details
            </legend>
            <div className="row">
              <div className="col-md-6">
                <div className="form-group">
                  <label for="id">Case Number</label>
                  <input type="text" className="form-control" id="id" placeholder="Case Number" {...id} />
                  {id.error && id.touched && <div className="alert alert-danger" role="alert">{id.error}</div>}
                </div>
              </div>
              <div className="col-md-6">
                <div className="form-group">
                  <label for="staffName">Staff Name</label>
                  <input type="text" className="form-control" id="staffName" placeholder="Case Owner" {...staffName} />
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-md-6">
                <div className="form-group">
                  <label for="status">Status</label>
                  <input type="text" className="form-control" id="status" placeholder="Status" {...status} />
                </div>
              </div>
              <div className="col-md-6">
                <div className="form-group">
                  <label for="objective">Objective</label>
                  <input type="text" className="form-control" id="objective" placeholder="Objective" {...objective} />
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-md-6">
                <div className="form-group">
                  <label for="dateOpened">Date Opened</label>
                  <input type="text" className="form-control" id="dateOpened" placeholder="Date Opened" {...dateOpened} />
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-md-10">
                &nbsp;
              </div>
              <div className="col-md-2">
                <button type="submit" className="btn btn-primary pull-right">Save Case</button>
              </div>
            </div>
          </fieldset>
        </form>
      </div>
    );
  }
}

function validateCase(data, props) {
  const errors = {}
  if (!data.id) {
    errors.id = 'Required'
  }
  return errors
}

EditCase.propTypes = {
  router: React.PropTypes.shape({
    push: React.PropTypes.func.isRequired
  }).isRequired
};

export default reduxForm({
    fields: ['id', 'staffName', 'status', 'objective', 'dateOpened'],
    form: 'editCase',
     validate: validateCase
  },
  state => ({
      initialValues: selectCaseById(state, state.selectedCase)
  })
)(withRouter(EditCase));
