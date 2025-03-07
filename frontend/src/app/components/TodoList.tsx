"use client";

import { useState, useRef } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { v4 as uuidv4 } from "uuid"; // Importing the uuid function

interface Todo {
  id: string; // Use UUID as string for uniqueness
  title: string;
  description?: string;
  completed: boolean;
}

const TodoList: React.FC = () => {
  const [pendingTasks, setPendingTasks] = useState<Todo[]>([]);
  const [completedTasks, setCompletedTasks] = useState<Todo[]>([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const titleRef = useRef<HTMLInputElement>(null);
  const descRef = useRef<HTMLTextAreaElement>(null);

  // Handle adding a new task
  const handleAddTask = () => {
    if (!title.trim()) return;

    const newTask: Todo = {
      id: uuidv4(), // Use uuid for generating a unique ID
      title,
      description: description.trim() || undefined,
      completed: false,
    };

    setPendingTasks((prev) => [...prev, newTask]);
    setTitle("");
    setDescription("");
    titleRef.current?.focus();
  };

  // Handle key navigation
  const handleKeyDown = (e: React.KeyboardEvent, field: "title" | "description") => {
    if (e.key === "Enter") {
      e.preventDefault();
      if (field === "title") {
        descRef.current?.focus();
      } else {
        handleAddTask();
      }
    }
  };

  // Toggle task completion status
  const handleToggleComplete = (id: string) => {
    const taskToComplete = pendingTasks.find((task) => task.id === id);
    const taskToUncomplete = completedTasks.find((task) => task.id === id);

    if (taskToComplete) {
      setPendingTasks((prev) => prev.filter((task) => task.id !== id));
      setCompletedTasks((prev) => [...prev, { ...taskToComplete, completed: true }]);
    } else if (taskToUncomplete) {
      setCompletedTasks((prev) => prev.filter((task) => task.id !== id));
      setPendingTasks((prev) => [...prev, { ...taskToUncomplete, completed: false }]);
    }
  };

  // Edit task details
  const handleEditTask = (id: string) => {
    const updatedTitle = prompt("Edit title:", pendingTasks.find((t) => t.id === id)?.title || "");
    const updatedDesc = prompt("Edit description:", pendingTasks.find((t) => t.id === id)?.description || "");

    if (updatedTitle !== null) {
      setPendingTasks((prev) =>
        prev.map((task) =>
          task.id === id ? { ...task, title: updatedTitle, description: updatedDesc || undefined } : task
        )
      );
    }
  };

  // Delete task
  const handleDeleteTask = (id: string) => {
    setPendingTasks((prev) => prev.filter((task) => task.id !== id));
    setCompletedTasks((prev) => prev.filter((task) => task.id !== id));
  };

  return (
    <div className="max-w-2xl mx-auto mt-10 p-6 bg-pink-50 text-gray-900 rounded-lg shadow-lg">
      <h1 className="text-3xl font-bold text-center mb-6 text-pink-600">🎀 Todo List</h1>

      {/* Task Input Form */}
      <div className="bg-white p-6 rounded-lg shadow-md mb-6">
        <input
          ref={titleRef}
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          onKeyDown={(e) => handleKeyDown(e, "title")}
          placeholder="Enter task title"
          className="w-full p-4 text-lg bg-gray-100 text-gray-900 border border-pink-300 rounded-md focus:outline-none focus:ring-2 focus:ring-pink-500 transition-all mb-3"
        />
        <textarea
          ref={descRef}
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          onKeyDown={(e) => handleKeyDown(e, "description")}
          placeholder="Enter task description (optional)"
          className="w-full p-4 text-lg bg-gray-100 text-gray-900 border border-pink-300 rounded-md focus:outline-none focus:ring-2 focus:ring-pink-500 transition-all mb-3"
        />
        <button
          onClick={handleAddTask}
          className="w-full bg-yellow-400 text-white py-3 rounded-md text-lg font-medium hover:bg-yellow-500 transition"
        >
          Add Task
        </button>
      </div>

      {/* Pending Tasks */}
      <AnimatePresence>
        {pendingTasks.length > 0 && (
          <ul className="space-y-4">
            {pendingTasks.map((task) => (
              <motion.li
                key={task.id} // Ensure the key is unique and stable
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: 10 }}
                className="bg-white p-6 rounded-lg shadow-lg flex justify-between items-center gap-4 hover:shadow-2xl transition-all"
              >
                <div className="flex-1">
                  <h3 className="text-xl font-semibold text-pink-600">{task.title}</h3>
                  {task.description && <p className="text-gray-700 mt-2">{task.description}</p>}
                </div>
                <div className="flex gap-2 items-center">
                  <button
                    onClick={() => handleToggleComplete(task.id)}
                    className="bg-yellow-400 text-white px-4 py-2 rounded-md hover:bg-yellow-500 transition"
                  >
                    Mark as Completed
                  </button>
                  <button
                    onClick={() => handleEditTask(task.id)}
                    className="bg-pink-500 text-white px-4 py-2 rounded-md hover:bg-pink-600 transition"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDeleteTask(task.id)}
                    className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition"
                  >
                    Delete 🗑️
                  </button>
                </div>
              </motion.li>
            ))}
          </ul>
        )}
      </AnimatePresence>

      {/* Divider & Completed Tasks */}
      <AnimatePresence>
        {completedTasks.length > 0 && (
          <>
            <hr className="border-pink-400 my-8" />
            <h2 className="text-center text-pink-600 text-2xl mb-3">✅ Completed Tasks</h2>
            <ul className="space-y-4">
              {completedTasks.map((task) => (
                <motion.li
                  key={task.id} // Ensure the key is unique and stable
                  initial={{ opacity: 0, y: -10 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: 10 }}
                  className="bg-white p-6 rounded-lg shadow-lg flex justify-between items-center gap-4 opacity-50 line-through"
                >
                  <div className="flex-1">
                    <h3 className="text-xl font-semibold text-pink-600">{task.title}</h3>
                    {task.description && <p className="text-gray-700 mt-2">{task.description}</p>}
                  </div>
                  <div className="flex gap-2 items-center">
                    <button
                      onClick={() => handleToggleComplete(task.id)}
                      className="bg-pink-500 text-white px-4 py-2 rounded-md hover:bg-pink-600 transition"
                    >
                      Move Back
                    </button>
                    <button
                      onClick={() => handleDeleteTask(task.id)}
                      className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition"
                    >
                      Delete 🗑️
                    </button>
                  </div>
                </motion.li>
              ))}
            </ul>
          </>
        )}
      </AnimatePresence>
    </div>
  );
};

export default TodoList;
