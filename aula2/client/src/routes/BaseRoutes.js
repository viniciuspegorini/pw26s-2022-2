import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import AuthenticatedRoutes from './AuthenticatedRoutes';
import SignRoutes from './SignRoutes';

const BaseRoutes = () => {
    const { authenticated } = useContext(AuthContext);

    return authenticated ? <AuthenticatedRoutes /> : <SignRoutes />;
}

export default BaseRoutes;