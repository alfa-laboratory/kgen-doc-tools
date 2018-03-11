import React from 'react';
import {Link} from 'react-router-dom';

const Navigation = () => (
  <div>
    <ul>
      <li><Link to="/portal">Home</Link></li>
      <li><Link to="/portal/about">About</Link></li>
      <li><Link to="/portal/sample">Sample</Link></li>
    </ul>
  </div>
);

export default Navigation;
