'use client';
import React, { useState } from 'react';
import {
  LayoutProps,
  RankedTester,
  rankWith,
  uiTypeIs,
} from '@jsonforms/core';
import { JsonFormsDispatch,withJsonFormsLayoutProps } from '@jsonforms/react';

const CustomCategoryStepper = ({ uischema, schema, path, renderers }: LayoutProps) => {
  const categories = uischema.elements || [];
  const [activeCategoryIndex, setActiveCategoryIndex] = useState(0);

  const handleNext = () => {
    if (activeCategoryIndex < categories.length - 1) {
      setActiveCategoryIndex(activeCategoryIndex + 1);
    }
  };

  const handleBack = () => {
    if (activeCategoryIndex > 0) {
      setActiveCategoryIndex(activeCategoryIndex - 1);
    }
  };

  const handleSubmit = () => {
    alert('Form Submitted!');
    // You can trigger a real submit action here
  };

  const activeCategory = categories[activeCategoryIndex];

  return (
    <div>
      <h3 className="text-xl font-bold mb-4">{activeCategory.label}</h3>
      <div className="border p-4 rounded-md bg-gray-50">
        <JsonFormsDispatch
          uischema={activeCategory}
          schema={schema}
          path={path}
          renderers={renderers}
        />
      </div>

      <div className="flex justify-between mt-6">
        <button
          onClick={handleBack}
          disabled={activeCategoryIndex === 0}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Back
        </button>

        {activeCategoryIndex < categories.length - 1 ? (
          <button
            onClick={handleNext}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Next
          </button>
        ) : (
          <button
            onClick={handleSubmit}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
          >
            Submit
          </button>
        )}
      </div>
    </div>
  );
};

export default withJsonFormsLayoutProps(CustomCategoryStepper);

// Tester to hook it into JSONForms
export const customCategoryTester: RankedTester = rankWith(3, uiTypeIs('Categorization'));