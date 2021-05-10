import React,{useState} from "react";
import { useFormik } from "formik";
import styled from "styled-components";

import FormInput from "../../../DesignSystem/Form/FormInput";
import SubmitButton from "../../../DesignSystem/Form/SubmitButton";
import Title from "../../../DesignSystem/Title";

const FormWrapper = styled.div`
  flex: 1 1 auto;
  width: 100%;
`;

interface FormValues {
  name: string;
  email: string;
  address: string;
}


const EligibilityApplication = () => {
  const [result, setResult] = useState({"eligibleCards":[]});

  const checkForEligibility = (values: FormValues) => {
    console.log("Form Values" ,values)
    fetch('http://localhost:8080/eligibility/check', {
      method: 'POST',
      body: JSON.stringify({"name": values.name,
                            "address": values.address,
                            "email": values.email }),
      headers: { 'Content-Type': 'application/json',
                  'Access-Control-Allow-Origin': '*',
                },
    })
      .then(res => res.json())
      .then(json => setResult(json))
  }  

  const { handleChange, handleSubmit, values } = useFormik<FormValues>({
    initialValues: {
      name: "",
      email: "",
      address: "",
    },
    onSubmit: (values) => 
    {
      checkForEligibility(values)
    },
  });
  return (
    <FormWrapper>
      <Title>Cards</Title>
      <form onSubmit={handleSubmit}>
        <FormInput
          type="text"
          name="name"
          id="name"
          onChange={handleChange}
          value={values.name}
          placeholder="Name"
        />
        <FormInput
          type="email"
          name="email"
          id="email"
          onChange={handleChange}
          value={values.email}
          placeholder="Email"
        />
        <FormInput
          type="text"
          name="address"
          id="address"
          onChange={handleChange}
          value={values.address}
          placeholder="Address"
        />
        <SubmitButton text="Submit" />
        {result.eligibleCards && result.eligibleCards.length ?
        <label>You are eligible for credit card : {result.eligibleCards.length ==2 ? result.eligibleCards[0] + " and " + result.eligibleCards[1] :result.eligibleCards }</label>
        :<label>You are eligible for credit card : None </label> }
        
      </form>
    </FormWrapper>
  );
};

export default EligibilityApplication;
