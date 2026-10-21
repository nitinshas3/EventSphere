import { useEffect } from "react";
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router";

const LoginPage: React.FC = () => {
  const { isLoading, isAuthenticated, signinRedirect } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoading) {
      return;
    }
    if (!isAuthenticated) {
      signinRedirect();
    } else {
      const redirectPath = localStorage.getItem("redirectPath") || "/dashboard";
      localStorage.removeItem("redirectPath");
      navigate(redirectPath);
    }
  }, [isLoading, isAuthenticated, signinRedirect, navigate]);

  return (
    <div className="bg-black min-h-screen text-white flex items-center justify-center">
      <p>Redirecting to login...</p>
    </div>
  );
};

export default LoginPage;
