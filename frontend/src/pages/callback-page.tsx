import { useEffect } from "react";
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router";

const CallbackPage: React.FC = () => {
  const { isLoading, isAuthenticated, error } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoading) {
      return;
    }

    if (isAuthenticated) {
      const redirectPath = localStorage.getItem("redirectPath") || "/dashboard";
      localStorage.removeItem("redirectPath");
      navigate(redirectPath);
    }
  }, [isLoading, isAuthenticated, navigate]);

  if (isLoading) {
    return (
      <div className="bg-black min-h-screen text-white flex items-center justify-center">
        <p>Processing login...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-black min-h-screen text-white flex flex-col items-center justify-center gap-4">
        <p className="text-red-500 font-semibold">Login Error: {error.message}</p>
        <button
          onClick={() => navigate("/")}
          className="bg-gray-800 hover:bg-gray-700 text-white px-4 py-2 rounded cursor-pointer"
        >
          Return Home
        </button>
      </div>
    );
  }

  return (
    <div className="bg-black min-h-screen text-white flex items-center justify-center">
      <p>Completing login...</p>
    </div>
  );
};

export default CallbackPage;
